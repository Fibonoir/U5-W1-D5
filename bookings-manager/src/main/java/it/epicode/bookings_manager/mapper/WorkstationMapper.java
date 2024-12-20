package it.epicode.bookings_manager.mapper;

import it.epicode.bookings_manager.dto.WorkstationResponse;
import it.epicode.bookings_manager.entity.Workstation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class WorkstationMapper {

    private final ModelMapper modelMapper;

    public WorkstationMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public WorkstationResponse toDto(Workstation entity) {
        WorkstationResponse dto = modelMapper.map(entity, WorkstationResponse.class);
        dto.setType(entity.getType().name());
        dto.setBuildingName(entity.getBuilding().getName());
        dto.setBuildingCity(entity.getBuilding().getCity());
        return dto;
    }
}