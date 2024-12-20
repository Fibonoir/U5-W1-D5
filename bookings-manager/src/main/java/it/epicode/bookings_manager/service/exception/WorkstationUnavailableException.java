package it.epicode.bookings_manager.service.exception;

public class WorkstationUnavailableException extends RuntimeException {
    public WorkstationUnavailableException(String message) {
        super(message);
    }
}
