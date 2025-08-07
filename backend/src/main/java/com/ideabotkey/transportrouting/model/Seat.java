package com.ideabotkey.transportrouting.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.ideabotkey.transportrouting.model.SeatStatus;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    /**
     * Vehicle this seat belongs to.
     */
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    @ToString.Exclude
    private Vehicle vehicle;

    /** Row number of the seat in the vehicle layout. */
    @Column(name = "seat_row_number")
    private Integer rowNumber;

    /** Seat label shown to users. */
    private String seatNumber;

    /** Price for booking this seat. */
    private Double price;

    /** Current seat status. */
    @Enumerated(EnumType.STRING)
    private SeatStatus status; // AVAILABLE, BOOKED

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Seat() {}

    public Seat(Vehicle vehicle, Integer rowNumber, String seatNumber, Double price, SeatStatus status) {
        this.vehicle = vehicle;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.price = price;
        this.status = status;
    }
}
