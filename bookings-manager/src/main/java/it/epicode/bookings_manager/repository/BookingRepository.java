package it.epicode.bookings_manager.repository;

import it.epicode.bookings_manager.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existsByUser_IdAndDate(Long userId, LocalDate date);
    boolean existsByWorkstation_IdAndDate(Long workstationId, LocalDate date);
    List<Booking> findByWorkstation_Id(Long workstationId);
}