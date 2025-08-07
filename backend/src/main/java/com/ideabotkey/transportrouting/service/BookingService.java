package com.ideabotkey.transportrouting.service;

import com.ideabotkey.transportrouting.dto.BookingDto;
import com.ideabotkey.transportrouting.dto.BookingRequest;
import com.ideabotkey.transportrouting.dto.BookingSeatEmployeeDto;
import com.ideabotkey.transportrouting.model.*;
import com.ideabotkey.transportrouting.repository.BookingRepository;
import com.ideabotkey.transportrouting.repository.SeatRepository;
import com.ideabotkey.transportrouting.repository.TripRepository;
import com.ideabotkey.transportrouting.repository.UserRepository;
import com.ideabotkey.transportrouting.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;

/**
 * Service layer for booking creation and validation.
 */
@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepo; // Assuming you have a UserRepository to fetch user details

    public BookingService(BookingRepository bookingRepository,
            SeatRepository seatRepository,
            TripRepository tripRepository,
            UserRepository userRepo) {
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
        this.tripRepository = tripRepository;
        this.userRepo = userRepo;
    }

    /**
     * Create bookings for the given request.
     * This method checks for overlapping bookings to avoid double booking a seat.
     */
    public List<BookingDto> createBooking(BookingRequest request) {
        Trip trip = tripRepository.findById(request.getTripId())
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found"));

        List<BookingDto> result = new ArrayList<>();
        for (Long seatId : request.getSeatIds()) {
            Seat seat = seatRepository.findById(seatId)
                    .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));

            // Check for overlaps
            List<Booking> overlaps = bookingRepository.findOverlappingBookings(
                    trip.getId(), seat.getId(), BookingStatus.CANCELLED,
                    request.getStartDate(), request.getEndDate());
            if (!overlaps.isEmpty()) {
                throw new RuntimeException("Seat " + seat.getSeatNumber() + " already booked for selected dates");
            }

            Booking b = new Booking();
            b.setUserId(request.getUserId());
            b.setTrip(trip);
            b.setSeat(seat);
            b.setBookingDate(LocalDateTime.now());
            b.setStartDate(request.getStartDate());
            b.setEndDate(request.getEndDate());
            b.setStatus(BookingStatus.CONFIRMED);
            b.setCreatedAt(LocalDateTime.now());
            b.setUpdatedAt(LocalDateTime.now());

            Booking saved = bookingRepository.save(b);
            BookingDto dto = toDto(saved);
            result.add(dto);
        }
        return result;
    }

    /**
     * Fetch bookings for a trip on a particular date.
     */
    public List<BookingDto> getBookingsForTripAndDate(Long tripId, LocalDate date) {
        List<Booking> bookings = bookingRepository.findByTripIdAndDate(tripId, date);
        return bookings.stream().map(this::toDto).toList();
    }

    /**
     * Fetch bookings for a trip across a date range.
     * Useful for calendar-based scheduling views.
     */
    public List<BookingDto> getBookingsForTripInRange(Long tripId, LocalDate startDate, LocalDate endDate) {
        List<Booking> bookings = bookingRepository.findByTripIdAndDateRange(tripId, startDate, endDate);
        return bookings.stream().map(this::toDto).toList();
    }

    /**
     * Fetch seat and employee details for all bookings on a trip.
     */
    public List<BookingSeatEmployeeDto> getSeatEmployeeList(Long tripId) {
        return bookingRepository.findSeatEmployeesByTripId(tripId);
    }

    private BookingDto toDto(Booking booking) {
        BookingDto dto = new BookingDto();
        dto.setId(booking.getId());
        dto.setUserId(booking.getUserId());
        dto.setTripId(booking.getTrip() != null ? booking.getTrip().getId() : null);
        dto.setSeatId(booking.getSeat() != null ? booking.getSeat().getId() : null);
        dto.setBookingDate(booking.getBookingDate()); // Or .toLocalDate() if you want only date
        dto.setStartDate(booking.getStartDate());
        dto.setEndDate(booking.getEndDate());
        dto.setStatus(booking.getStatus());

        dto.setRouteName(booking.getTrip() != null && booking.getTrip().getRoute() != null
                ? booking.getTrip().getRoute().getName()
                : "");
        dto.setTripTime(booking.getTrip() != null
                ? booking.getTrip().getStartTime() + " - " + booking.getTrip().getEndTime()
                : "");
        dto.setUserName(
                userRepo.findById(booking.getUserId())
                        .map(User::getUsername)
                        .orElse(""));

        dto.setTotalAmount(booking.getSeat() != null ? booking.getSeat().getPrice() : 0.0);

        return dto;
    }

    public List<BookingDto> getRecentBookings(int limit) {
        // Fetch most recent bookings (by createdAt desc)
        var bookings = bookingRepository.findAll(
                PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "createdAt"))).getContent();

        // Log each booking with minimal info
        System.out.println("Fetched " + bookings.size() + " recent bookings:");
        for (var b : bookings) {
            System.out.println("Booking #" + b.getId() +
                    ", trip=" + (b.getTrip() != null ? b.getTrip().getId() : null) +
                    ", user=" + b.getUserId() +
                    ", createdAt=" + b.getCreatedAt());
        }

        return bookings.stream()
                .map(this::toDto)
                .toList();
    }

}
