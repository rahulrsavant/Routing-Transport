package com.ideabotkey.transportrouting.controller;

import com.ideabotkey.transportrouting.model.Seat;
import com.ideabotkey.transportrouting.repository.SeatRepository;
import com.ideabotkey.transportrouting.repository.UserRepository;
import com.ideabotkey.transportrouting.repository.TripRepository;
import com.ideabotkey.transportrouting.repository.BookingRepository;
import com.ideabotkey.transportrouting.service.PermissionChecker;
import com.ideabotkey.transportrouting.exception.ResourceNotFoundException;
import com.ideabotkey.transportrouting.dto.SeatAvailabilityDto;
import com.ideabotkey.transportrouting.dto.SeatDto;
import com.ideabotkey.transportrouting.model.Booking;
import com.ideabotkey.transportrouting.model.BookingStatus;
import com.ideabotkey.transportrouting.model.Trip;
import com.ideabotkey.transportrouting.model.SeatStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/seats")
public class SeatController extends BaseController {
    private final SeatRepository seatRepository;
    private final BookingRepository bookingRepository;
    private final TripRepository tripRepository;

    public SeatController(SeatRepository repository, BookingRepository bookingRepository,
            TripRepository tripRepository, UserRepository userRepository, PermissionChecker checker) {
        super(userRepository, checker);
        this.seatRepository = repository;
        this.bookingRepository = bookingRepository;
        this.tripRepository = tripRepository;
    }

    @GetMapping
    public List<SeatDto> list(@RequestParam(required = false) Long vehicleId, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "VIEW_SEAT");
        List<Seat> seats = vehicleId != null ? seatRepository.findByVehicleId(vehicleId) : seatRepository.findAll();
        return seats.stream().map(this::toDto).toList();
    }

    @PostMapping("/book")
    public List<Seat> book(@RequestBody SeatBookRequest request, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "BOOK_SEAT");
        List<Seat> seats = seatRepository.findAllById(request.getSeatIds());
        seats.forEach(s -> s.setStatus(SeatStatus.BOOKED));
        return seatRepository.saveAll(seats);
    }

    /**
     * Get seat availability for a trip on a specific date.
     */
    @GetMapping("/availability")
    public List<SeatAvailabilityDto> availability(@RequestParam("trip_id") Long tripId,
            @RequestParam("date") String date,
            @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "VIEW_SEAT");
        LocalDate d = LocalDate.parse(date);
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found"));
        List<Seat> seats = seatRepository.findByVehicleId(trip.getVehicle().getId());
        return seats.stream().map(seat -> {
            List<Booking> overlap = bookingRepository.findOverlappingBookings(
                    tripId, seat.getId(), BookingStatus.CANCELLED, d, d);
            SeatAvailabilityDto dto = new SeatAvailabilityDto();
            dto.setSeatId(seat.getId());
            dto.setSeatNumber(seat.getSeatNumber());
            dto.setAvailable(overlap.isEmpty());
            return dto;
        }).toList();
    }

    /**
     * Get seat availability for a trip across a date range.
     * Returns whether each seat is free for the entire range.
     */
    @GetMapping("/availability-range")
    public List<SeatAvailabilityDto> availabilityRange(@RequestParam("trip_id") Long tripId,
            @RequestParam("start_date") String start,
            @RequestParam("end_date") String end,
            @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "VIEW_SEAT");
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found"));
        List<Seat> seats = seatRepository.findByVehicleId(trip.getVehicle().getId());
        return seats.stream().map(seat -> {
            List<Booking> overlap = bookingRepository.findOverlappingBookings(
                    tripId, seat.getId(), BookingStatus.CANCELLED, startDate, endDate);
            SeatAvailabilityDto dto = new SeatAvailabilityDto();
            dto.setSeatId(seat.getId());
            dto.setSeatNumber(seat.getSeatNumber());
            dto.setAvailable(overlap.isEmpty());
            return dto;
        }).toList();
    }

    private SeatDto toDto(Seat seat) {
        SeatDto dto = new SeatDto();
        dto.setId(seat.getId());
        if (seat.getVehicle() != null) {
            dto.setVehicleId(seat.getVehicle().getId());
        }
        dto.setRowNumber(seat.getRowNumber());
        dto.setSeatNumber(seat.getSeatNumber());
        dto.setPrice(seat.getPrice());
        dto.setStatus(seat.getStatus());
        return dto;
    }
}
