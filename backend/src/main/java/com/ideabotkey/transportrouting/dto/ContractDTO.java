package com.ideabotkey.transportrouting.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ContractDTO {

    private Long id;
    private String contractType;
    private BigDecimal amount;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;

    private Long companyId;
    private String companyName;

    private Long ownerId;
    private String ownerName;

    private Long vehicleId;
    private String vehicleNumber;

    // No-arg constructor
    public ContractDTO() {
    }

    // All-arg constructor
    public ContractDTO(Long id, String contractType, BigDecimal amount, String status, LocalDate startDate,
            LocalDate endDate, Long companyId, String companyName, Long ownerId, String ownerName,
            Long vehicleId, String vehicleNumber) {
        this.id = id;
        this.contractType = contractType;
        this.amount = amount;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.companyId = companyId;
        this.companyName = companyName;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.vehicleId = vehicleId;
        this.vehicleNumber = vehicleNumber;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }
}
