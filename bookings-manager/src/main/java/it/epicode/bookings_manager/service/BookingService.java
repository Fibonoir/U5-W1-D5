package it.epicode.bookings_manager.service;

import it.epicode.bookings_manager.entity.Booking;
import it.epicode.bookings_manager.entity.User;
import it.epicode.bookings_manager.entity.Workstation;
import it.epicode.bookings_manager.repository.BookingRepository;
import it.epicode.bookings_manager.service.exception.BookingException;
import it.epicode.bookings_manager.service.exception.WorkstationUnavailableException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final WorkstationService workstationService;

    public BookingService(BookingRepository bookingRepository, UserService userService, WorkstationService workstationService) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.workstationService = workstationService;
    }

    @Transactional
    public Booking bookWorkstation(String username, Long workstationId, LocalDate date) {
        User user = userService.getByUsername(username);
        Workstation workstation = workstationService.getById(workstationId);

        // Check if user already booked a workstation on this date
        if (bookingRepository.existsByUser_IdAndDate(user.getId(), date)) {
            throw new BookingException("User already has a booking on " + date);
        }

        // Check if workstation is available on this date
        if (bookingRepository.existsByWorkstation_IdAndDate(workstationId, date)) {
            throw new WorkstationUnavailableException("Workstation is not available on " + date);
        }

        Booking booking = Booking.builder()
                .user(user)
                .workstation(workstation)
                .date(date)
                .build();
        return bookingRepository.save(booking);
    }
}