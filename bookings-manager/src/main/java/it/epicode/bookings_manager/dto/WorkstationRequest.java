package it.epicode.bookings_manager.dto;

import it.epicode.bookings_manager.entity.Workstation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WorkstationRequest {
    @NotBlank
    private String code;
    @NotBlank
    private String description;
    @NotNull
    private Workstation.WorkstationType type;
    @NotNull
    private Integer maxOccupants;
    @NotNull
    private Long buildingId;
}