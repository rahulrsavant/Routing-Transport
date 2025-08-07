package com.ideabotkey.transportrouting.dto;

import com.ideabotkey.transportrouting.model.SeatStatus;

/**
 * Data transfer object for Seat.
 */
public class SeatDto {
    private Long id;
    private Long vehicleId;
    private Integer rowNumber;
    private String seatNumber;
    private Double price;
    private SeatStatus status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getVehicleId() { return vehicleId; }
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }

    public Integer getRowNumber() { return rowNumber; }
    public void setRowNumber(Integer rowNumber) { this.rowNumber = rowNumber; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public SeatStatus getStatus() { return status; }
    public void setStatus(SeatStatus status) { this.status = status; }
}
