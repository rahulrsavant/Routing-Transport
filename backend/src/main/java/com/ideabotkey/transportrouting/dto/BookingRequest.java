package com.ideabotkey.transportrouting.dto;

import java.time.LocalDate;
import java.util.List;

/**
 * Request payload for creating bookings.
 */
public class BookingRequest {
    private Long userId;
    private Long tripId;
    private List<Long> seatIds;
    private LocalDate startDate;
    private LocalDate endDate;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getTripId() { return tripId; }
    public void setTripId(Long tripId) { this.tripId = tripId; }

    public List<Long> getSeatIds() { return seatIds; }
    public void setSeatIds(List<Long> seatIds) { this.seatIds = seatIds; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}
