package it.epicode.bookings_manager.service;

import it.epicode.bookings_manager.entity.Workstation;
import it.epicode.bookings_manager.repository.WorkstationRepository;
import it.epicode.bookings_manager.service.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkstationService {

    private final WorkstationRepository workstationRepository;

    public WorkstationService(WorkstationRepository workstationRepository) {
        this.workstationRepository = workstationRepository;
    }

    public Workstation getById(Long id) {
        return workstationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workstation not found with id: " + id));
    }

    public List<Workstation> search(Workstation.WorkstationType type, String city) {
        return workstationRepository.findByTypeAndBuilding_City(type, city);
    }

    public Workstation createWorkstation(Workstation workstation) {
        return workstationRepository.save(workstation);
    }
}
