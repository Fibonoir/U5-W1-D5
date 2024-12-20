package it.epicode.bookings_manager.config;

import it.epicode.bookings_manager.entity.Building;
import it.epicode.bookings_manager.entity.Workstation;
import it.epicode.bookings_manager.repository.BuildingRepository;
import it.epicode.bookings_manager.repository.WorkstationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StaticDataLoader {

    @Bean
    CommandLineRunner loadStaticWorkstations(BuildingRepository buildingRepository, WorkstationRepository workstationRepository) {
        return args -> {
            // Define cities
            String[] cities = {"Rome", "Milan", "Naples", "Turin", "Florence"};

            // Loop through each city and create workstations
            for (String city : cities) {
                Building building = buildingRepository.save(
                        Building.builder()
                                .name(city + " HQ")
                                .address("123 " + city + " Street")
                                .city(city)
                                .build()
                );

                // Add at least one workstation of each type
                workstationRepository.save(Workstation.builder()
                        .code("WS-" + city + "-PRIVATE")
                        .description("Private Desk in " + city)
                        .type(Workstation.WorkstationType.PRIVATE)
                        .maxOccupants(1)
                        .building(building)
                        .build()
                );

                workstationRepository.save(Workstation.builder()
                        .code("WS-" + city + "-OPENSPACE")
                        .description("Openspace Desk in " + city)
                        .type(Workstation.WorkstationType.OPENSPACE)
                        .maxOccupants(5)
                        .building(building)
                        .build()
                );

                workstationRepository.save(Workstation.builder()
                        .code("WS-" + city + "-MEETING")
                        .description("Meeting Room in " + city)
                        .type(Workstation.WorkstationType.MEETING_ROOM)
                        .maxOccupants(10)
                        .building(building)
                        .build()
                );

                // Add additional workstations for variety
                for (int i = 1; i <= 3; i++) {
                    workstationRepository.save(Workstation.builder()
                            .code("WS-" + city + "-ADD-" + i)
                            .description("Additional Desk " + i + " in " + city)
                            .type(Workstation.WorkstationType.values()[i % 3]) // Rotate types
                            .maxOccupants(1 + (i * 2)) // Different max occupants
                            .building(building)
                            .build()
                    );
                }
            }
        };
    }
}
