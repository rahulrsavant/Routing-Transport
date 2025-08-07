package com.ideabotkey.transportrouting.controller;

import com.ideabotkey.transportrouting.dto.BookingDto;
import com.ideabotkey.transportrouting.dto.BookingRequest;
import com.ideabotkey.transportrouting.dto.BookingSeatEmployeeDto;
import com.ideabotkey.transportrouting.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest; // Add this import at the top


/**
 * Controller for managing bookings.
 */
@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * Create a booking for one or more seats.
     */
    @PostMapping
    public List<BookingDto> create(@RequestBody BookingRequest request) {
        return bookingService.createBooking(request);
    }

    /**
     * Get bookings for a trip on a specific date.
     */
    @GetMapping
    public List<BookingDto> list(@RequestParam("trip_id") Long tripId,
                                 @RequestParam("date") String date) {
        LocalDate d = LocalDate.parse(date);
        return bookingService.getBookingsForTripAndDate(tripId, d);
    }

    /**
     * Get bookings for a trip across a date range for calendar views.
     */
    @GetMapping("/calendar")
    public List<BookingDto> calendar(@RequestParam("trip_id") Long tripId,
                                     @RequestParam("start_date") String start,
            @RequestParam("end_date") String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return bookingService.getBookingsForTripInRange(tripId, startDate, endDate);
    }
    

    // ... your other code ...

    /**
     * Get recent bookings (most recent first, limited by 'limit' param).
     */
    @GetMapping("/recent")
    public List<BookingDto> recent(@RequestParam(defaultValue = "5") int limit) {
        return bookingService.getRecentBookings(limit);
    }

    /**
     * Get booked seats with employee details for a trip.
     */
    @GetMapping("/trip/{tripId}/seat-employee")
    public List<BookingSeatEmployeeDto> seatEmployee(@PathVariable Long tripId) {
        return bookingService.getSeatEmployeeList(tripId);
    }

}
