package com.ideabotkey.transportrouting.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "vehicles")
public class Vehicle {

    public Vehicle(VehicleOwner owner, String vehicleType, String registrationNumber, Integer capacity,
            String model, String status) {
        super();
        // this.id = id;
        this.owner = owner;
        this.vehicleType = vehicleType;
        this.registrationNumber = registrationNumber;
        this.capacity = capacity;
        this.model = model;
        this.status = status;
    }

    public Vehicle() {
        // TODO Auto-generated constructor stub
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @ToString.Exclude
    private VehicleOwner owner;

    private String vehicleType;
    private String registrationNumber;
    private Integer capacity;
    private String model;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public VehicleOwner getOwner() {
        return owner;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public String getModel() {
        return model;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOwner(VehicleOwner owner) {
        this.owner = owner;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Vehicle(Long id, VehicleOwner owner, String vehicleType, String registrationNumber, Integer capacity,
            String model, String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.owner = owner;
        this.vehicleType = vehicleType;
        this.registrationNumber = registrationNumber;
        this.capacity = capacity;
        this.model = model;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Vehicle [id=" + id + ", owner=" + owner + ", vehicleType=" + vehicleType + ", registrationNumber="
                + registrationNumber + ", capacity=" + capacity + ", model=" + model + ", status=" + status
                + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
    }

}
