package com.ideabotkey.transportrouting.repository;

import com.ideabotkey.transportrouting.model.Booking;
import com.ideabotkey.transportrouting.model.BookingStatus;
import com.ideabotkey.transportrouting.dto.BookingSeatEmployeeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/** Repository for Booking entity. */
public interface BookingRepository extends JpaRepository<Booking, Long> {

    /**
     * Find bookings overlapping the given date range for a seat on a trip.
     */
    @Query("select b from Booking b where b.trip.id = :tripId and b.seat.id = :seatId and b.status <> :cancelled and b.startDate <= :endDate and b.endDate >= :startDate")
    List<Booking> findOverlappingBookings(@Param("tripId") Long tripId,
            @Param("seatId") Long seatId,
            @Param("cancelled") BookingStatus cancelled,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    /**
     * Find all bookings for a trip on a specific date.
     */
    @Query("select b from Booking b where b.trip.id = :tripId and b.startDate <= :date and b.endDate >= :date")
    List<Booking> findByTripIdAndDate(@Param("tripId") Long tripId, @Param("date") LocalDate date);

    /**
     * Find bookings for a trip overlapping the given date range.
     * Used for calendar views spanning multiple days.
     */
    @Query("select b from Booking b where b.trip.id = :tripId and b.startDate <= :endDate and b.endDate >= :startDate")
    List<Booking> findByTripIdAndDateRange(@Param("tripId") Long tripId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    Page<Booking> findAll(Pageable pageable);

    /**
     * Fetch seat and employee details for all bookings on a trip.
     */
    @Query("select new com.ideabotkey.transportrouting.dto.BookingSeatEmployeeDto(b.seat.seatNumber, e.id, e.name) " +
           "from Booking b join User u on u.id = b.userId join u.employee e " +
           "where b.trip.id = :tripId")
    List<BookingSeatEmployeeDto> findSeatEmployeesByTripId(@Param("tripId") Long tripId);

}
