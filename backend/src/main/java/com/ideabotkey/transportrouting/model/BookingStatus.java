package com.ideabotkey.transportrouting.model;

/**
 * Status of a booking.
 */
public enum BookingStatus {
    /** Booking is confirmed. */
    CONFIRMED,
    /** Booking is awaiting confirmation. */
    PENDING,
    /** Booking has been cancelled. */
    CANCELLED
}
