package com.ideabotkey.transportrouting.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "trips")
public class Trip {
    public Trip(Long id, Route route, Vehicle vehicle, Driver driver,
            LocalDate date, String startTime, String endTime, String status,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.route = route;
        this.vehicle = vehicle;
        this.driver = driver;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Trip() {
        // No-args constructor needed for tests and frameworks
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "route_id")
    @ToString.Exclude
    private Route route;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    @ToString.Exclude
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    @ToString.Exclude
    private Driver driver;

    private LocalDate date;
    private String startTime;
    private String endTime;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
