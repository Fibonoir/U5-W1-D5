package it.epicode.bookings_manager;

import it.epicode.bookings_manager.entity.Building;
import it.epicode.bookings_manager.entity.Workstation;
import it.epicode.bookings_manager.repository.BuildingRepository;
import it.epicode.bookings_manager.repository.WorkstationRepository;
import it.epicode.bookings_manager.service.WorkstationService;
import it.epicode.bookings_manager.service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class WorkstationServiceIntegrationTest {

    @Autowired
    private WorkstationService workstationService;

    @Autowired
    private WorkstationRepository workstationRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Test
    void testGetByIdThrowsExceptionIfNotFound() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            workstationService.getById(999L); // This ID doesn't exist
        });
    }

    @Test
    void testCreateAndRetrieveWorkstation() {
        Building building = buildingRepository.save(Building.builder()
                .name("Building X")
                .address("Test Road")
                .city("Milan")
                .build());

        Workstation saved = workstationService.createWorkstation(
                Workstation.builder()
                        .code("WS-ABC")
                        .description("Test Desk")
                        .type(Workstation.WorkstationType.PRIVATE)
                        .maxOccupants(2)
                        .building(building)
                        .build()
        );

        Workstation found = workstationService.getById(saved.getId());
        Assertions.assertNotNull(found);
        Assertions.assertEquals("WS-ABC", found.getCode());
        Assertions.assertEquals("Test Desk", found.getDescription());
    }
}
