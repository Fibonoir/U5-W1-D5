package it.epicode.bookings_manager;

import it.epicode.bookings_manager.entity.Building;
import it.epicode.bookings_manager.entity.Workstation;
import it.epicode.bookings_manager.repository.BuildingRepository;
import it.epicode.bookings_manager.repository.WorkstationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class WorkstationRepositoryTest {

    @Autowired
    private WorkstationRepository workstationRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Test
    void testFindByTypeAndBuildingCity() {
        // Given: A building and some workstations
        Building building = buildingRepository.save(
                Building.builder()
                        .name("Test Building")
                        .address("123 Test St")
                        .city("Rome")
                        .build()
        );

        workstationRepository.save(Workstation.builder()
                .code("WS-001")
                .description("Private Desk")
                .type(Workstation.WorkstationType.PRIVATE)
                .maxOccupants(1)
                .building(building)
                .build());

        workstationRepository.save(Workstation.builder()
                .code("WS-002")
                .description("Openspace Desk")
                .type(Workstation.WorkstationType.OPENSPACE)
                .maxOccupants(5)
                .building(building)
                .build());

        List<Workstation> result = workstationRepository.findByTypeAndBuilding_City(Workstation.WorkstationType.OPENSPACE, "Rome");

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("WS-002", result.get(0).getCode());
    }
}
