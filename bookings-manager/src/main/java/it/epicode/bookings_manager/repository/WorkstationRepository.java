package it.epicode.bookings_manager.repository;

import it.epicode.bookings_manager.entity.Workstation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkstationRepository extends JpaRepository<Workstation, Long> {
    List<Workstation> findByTypeAndBuilding_City(Workstation.WorkstationType type, String city);
}
