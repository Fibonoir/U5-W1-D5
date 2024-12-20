package it.epicode.bookings_manager.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "workstations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Workstation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    private String description;

    @Enumerated(EnumType.STRING)
    private WorkstationType type;

    private Integer maxOccupants;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    public enum WorkstationType {
        PRIVATE, OPENSPACE, MEETING_ROOM
    }
}