package it.epicode.bookings_manager.repository;

import it.epicode.bookings_manager.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building, Long> {
}