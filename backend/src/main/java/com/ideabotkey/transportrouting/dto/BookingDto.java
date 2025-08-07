package com.ideabotkey.transportrouting.dto;

import com.ideabotkey.transportrouting.model.BookingStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data transfer object for Booking entity.
 */
public class BookingDto {
    private Long id;
    private Long userId;
    private Long tripId;
    private Long seatId;
    private LocalDateTime bookingDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private BookingStatus status;

    // For Recent Bookings Widget
    private String routeName;
    private String tripTime;
    private String userName;
    private Double totalAmount;

    // --- Existing getters/setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getTripId() { return tripId; }
    public void setTripId(Long tripId) { this.tripId = tripId; }

    public Long getSeatId() { return seatId; }
    public void setSeatId(Long seatId) { this.seatId = seatId; }

    public LocalDateTime getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDateTime bookingDate) { this.bookingDate = bookingDate; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }

    // --- New getters/setters ---

    public String getRouteName() { return routeName; }
    public void setRouteName(String routeName) { this.routeName = routeName; }

    public String getTripTime() { return tripTime; }
    public void setTripTime(String tripTime) { this.tripTime = tripTime; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
}
