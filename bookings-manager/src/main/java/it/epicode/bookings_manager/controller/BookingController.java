package it.epicode.bookings_manager.controller;

import it.epicode.bookings_manager.dto.BookingRequest;
import it.epicode.bookings_manager.dto.BookingResponse;
import it.epicode.bookings_manager.mapper.BookingMapper;
import it.epicode.bookings_manager.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    public BookingController(BookingService bookingService, BookingMapper bookingMapper) {
        this.bookingService = bookingService;
        this.bookingMapper = bookingMapper;
    }

    @PostMapping("/{username}")
    public ResponseEntity<BookingResponse> bookWorkstation(@PathVariable String username, @Valid @RequestBody BookingRequest request) {
        var booking = bookingService.bookWorkstation(username, request.getWorkstationId(), request.getDate());
        return ResponseEntity.ok(bookingMapper.toDto(booking));
    }
}
