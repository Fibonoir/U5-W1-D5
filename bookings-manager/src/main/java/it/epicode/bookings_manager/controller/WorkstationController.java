package it.epicode.bookings_manager.controller;

import it.epicode.bookings_manager.dto.WorkstationRequest;
import it.epicode.bookings_manager.dto.WorkstationResponse;
import it.epicode.bookings_manager.entity.Building;
import it.epicode.bookings_manager.entity.Workstation;
import it.epicode.bookings_manager.mapper.WorkstationMapper;
import it.epicode.bookings_manager.repository.BuildingRepository;
import it.epicode.bookings_manager.service.WorkstationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/workstations")
public class WorkstationController {

    private final WorkstationService workstationService;
    private final WorkstationMapper workstationMapper;
    private final BuildingRepository buildingRepository;

    public WorkstationController(WorkstationService workstationService, WorkstationMapper workstationMapper, BuildingRepository buildingRepository) {
        this.workstationService = workstationService;
        this.workstationMapper = workstationMapper;
        this.buildingRepository = buildingRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkstationResponse> getWorkstation(@PathVariable Long id) {
        Workstation w = workstationService.getById(id);
        return ResponseEntity.ok(workstationMapper.toDto(w));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam("type") Workstation.WorkstationType type, @RequestParam("city") String city) {
        var list = workstationService.search(type, city).stream()
                .map(workstationMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<WorkstationResponse> create(@Valid @RequestBody WorkstationRequest request) {
        Building building = buildingRepository.findById(request.getBuildingId()).orElseThrow();
        Workstation w = Workstation.builder()
                .code(request.getCode())
                .description(request.getDescription())
                .type(request.getType())
                .maxOccupants(request.getMaxOccupants())
                .building(building)
                .build();
        Workstation saved = workstationService.createWorkstation(w);
        return ResponseEntity.ok(workstationMapper.toDto(saved));
    }
}
