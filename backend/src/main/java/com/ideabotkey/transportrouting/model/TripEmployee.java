package com.ideabotkey.transportrouting.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "trip_employees")
public class TripEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    @ToString.Exclude
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @ToString.Exclude
    private Employee employee;

    private String pickUpPoint;
    private String dropOffPoint;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
