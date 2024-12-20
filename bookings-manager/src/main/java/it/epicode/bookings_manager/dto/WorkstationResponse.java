package it.epicode.bookings_manager.dto;

import lombok.Data;

@Data
public class WorkstationResponse {
    private Long id;
    private String code;
    private String description;
    private String type;
    private Integer maxOccupants;
    private String buildingName;
    private String buildingCity;
}
