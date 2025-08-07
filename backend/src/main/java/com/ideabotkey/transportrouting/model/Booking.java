package com.ideabotkey.transportrouting.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Booking entity linking a user to a seat on a trip.
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    /** Identifier of the user who made the booking. */
    @Column(name = "user_id")
    private Long userId;

    /** Trip this booking belongs to. */
    @ManyToOne
    @JoinColumn(name = "trip_id")
    @ToString.Exclude
    private Trip trip;

    /** Seat reserved for the trip. */
    @ManyToOne
    @JoinColumn(name = "seat_id")
    @ToString.Exclude
    private Seat seat;

    /** Timestamp when the booking was created. */
    private LocalDateTime bookingDate;

    /** First date of reservation. */
    private LocalDate startDate;

    /** Last date of reservation (inclusive). */
    private LocalDate endDate;

    /** Current booking status. */
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Booking(Long id, Long userId, Trip trip, Seat seat, LocalDateTime bookingDate,
            LocalDate startDate, LocalDate endDate, BookingStatus status,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.trip = trip;
        this.seat = seat;
        this.bookingDate = bookingDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Booking() {
        // No-args constructor required by JPA
    }

}
