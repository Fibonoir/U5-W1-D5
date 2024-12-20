package it.epicode.bookings_manager.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequest {
    @NotNull
    private Long workstationId;
    @NotNull
    private LocalDate date;
}