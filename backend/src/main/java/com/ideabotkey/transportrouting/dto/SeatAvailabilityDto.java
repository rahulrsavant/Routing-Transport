package com.ideabotkey.transportrouting.dto;

/**
 * DTO representing seat availability for a specific date/trip.
 */
public class SeatAvailabilityDto {
    private Long seatId;
    private String seatNumber;
    private boolean available;

    public Long getSeatId() { return seatId; }
    public void setSeatId(Long seatId) { this.seatId = seatId; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}
