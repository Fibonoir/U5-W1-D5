package it.epicode.bookings_manager;

import it.epicode.bookings_manager.entity.Booking;
import it.epicode.bookings_manager.entity.Building;
import it.epicode.bookings_manager.entity.User;
import it.epicode.bookings_manager.entity.Workstation;
import it.epicode.bookings_manager.entity.Workstation.WorkstationType;
import it.epicode.bookings_manager.repository.BookingRepository;
import it.epicode.bookings_manager.repository.BuildingRepository;
import it.epicode.bookings_manager.repository.UserRepository;
import it.epicode.bookings_manager.repository.WorkstationRepository;
import it.epicode.bookings_manager.service.BookingService;
import it.epicode.bookings_manager.service.exception.BookingException;
import it.epicode.bookings_manager.service.exception.ResourceNotFoundException;
import it.epicode.bookings_manager.service.exception.WorkstationUnavailableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
@Transactional
class BookingServiceIntegrationTest {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkstationRepository workstationRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private BookingRepository bookingRepository;

    private User user;
    private Workstation workstation1;
    private Workstation workstation2;
    private LocalDate bookingDate;

    @BeforeEach
    void setUp() {
        // Create a building
        Building building = buildingRepository.save(
                Building.builder()
                        .name("Test Building")
                        .address("123 Test St")
                        .city("Rome")
                        .build()
        );

        // Create workstations
        workstation1 = workstationRepository.save(
                Workstation.builder()
                        .code("WS-001")
                        .description("Private Desk")
                        .type(WorkstationType.PRIVATE)
                        .maxOccupants(1)
                        .building(building)
                        .build()
        );

        workstation2 = workstationRepository.save(
                Workstation.builder()
                        .code("WS-002")
                        .description("Openspace Desk")
                        .type(WorkstationType.OPENSPACE)
                        .maxOccupants(5)
                        .building(building)
                        .build()
        );

        // Create a user
        user = userRepository.save(
                User.builder()
                        .username("jdoe")
                        .fullName("John Doe")
                        .email("jdoe@example.com")
                        .build()
        );

        // Define booking date
        bookingDate = LocalDate.now().plusDays(1); // Tomorrow
    }

    @Test
    void testSuccessfulBooking() {
        // When: Booking workstation1 for the user on bookingDate
        Booking booking = bookingService.bookWorkstation(user.getUsername(), workstation1.getId(), bookingDate);

        // Then: Booking is successful and persisted
        Assertions.assertNotNull(booking.getId());
        Assertions.assertEquals(user.getId(), booking.getUser().getId());
        Assertions.assertEquals(workstation1.getId(), booking.getWorkstation().getId());
        Assertions.assertEquals(bookingDate, booking.getDate());

        // Verify in repository
        Booking retrieved = bookingRepository.findById(booking.getId()).orElse(null);
        Assertions.assertNotNull(retrieved);
        Assertions.assertEquals("jdoe", retrieved.getUser().getUsername());
    }

    @Test
    void testUserCannotBookMultipleWorkstationsOnSameDate() {
        // Given: User has already booked workstation1 on bookingDate
        bookingService.bookWorkstation(user.getUsername(), workstation1.getId(), bookingDate);

        // When & Then: Attempting to book workstation2 on the same date should throw BookingException
        Assertions.assertThrows(BookingException.class, () -> {
            bookingService.bookWorkstation(user.getUsername(), workstation2.getId(), bookingDate);
        });
    }

    @Test
    void testWorkstationUnavailableOnDate() {
        // Given: Workstation1 is already booked on bookingDate by another user
        User anotherUser = userRepository.save(
                User.builder()
                        .username("asmith")
                        .fullName("Alice Smith")
                        .email("asmith@example.com")
                        .build()
        );
        bookingService.bookWorkstation(anotherUser.getUsername(), workstation1.getId(), bookingDate);

        // When & Then: Attempting to book workstation1 on the same date should throw WorkstationUnavailableException
        Assertions.assertThrows(WorkstationUnavailableException.class, () -> {
            bookingService.bookWorkstation(user.getUsername(), workstation1.getId(), bookingDate);
        });
    }

    @Test
    void testBookingWithNonExistentUser() {
        // When & Then: Booking with a non-existent username should throw ResourceNotFoundException
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            bookingService.bookWorkstation("nonexistent", workstation1.getId(), bookingDate);
        });
    }

    @Test
    void testBookingWithNonExistentWorkstation() {
        // When & Then: Booking with a non-existent workstation ID should throw ResourceNotFoundException
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            bookingService.bookWorkstation(user.getUsername(), 999L, bookingDate);
        });
    }
}
