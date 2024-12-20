package it.epicode.bookings_manager.dto;

import lombok.Data;

@Data
public class UserResponse {
    private String username;
    private String fullName;
    private String email;
}
