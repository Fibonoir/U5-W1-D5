package it.epicode.bookings_manager.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingResponse {
    private Long bookingId;
    private String username;
    private Long workstationId;
    private LocalDate date;
}
