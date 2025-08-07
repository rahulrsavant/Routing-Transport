package com.ideabotkey.transportrouting.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.*;
import lombok.ToString;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "drivers")
@ToString
public class Driver {

    public Driver(String name, String phone, String licenseNumber, String address, Vehicle assignedVehicle,
            String status) {
        super();
        // this.id = id;
        this.name = name;
        this.phone = phone;
        this.licenseNumber = licenseNumber;
        this.address = address;
        this.assignedVehicle = assignedVehicle;
        this.status = status;
    }

    public Driver() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String name;
    private String phone;
    private String licenseNumber;
    private String address;

    @ManyToOne
    @JoinColumn(name = "assigned_vehicle_id")
    @ToString.Exclude
    private Vehicle assignedVehicle;

    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
