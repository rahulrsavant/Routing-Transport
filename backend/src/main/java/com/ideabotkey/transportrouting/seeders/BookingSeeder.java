package com.ideabotkey.transportrouting.seeders;

import com.ideabotkey.transportrouting.model.*;
import com.ideabotkey.transportrouting.repository.BookingRepository;
import com.ideabotkey.transportrouting.repository.SeatRepository;
import com.ideabotkey.transportrouting.repository.TripRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Component
public class BookingSeeder implements CommandLineRunner {

    private final BookingRepository bookingRepository;
    private final TripRepository tripRepository;
    private final SeatRepository seatRepository;

    public BookingSeeder(
            BookingRepository bookingRepository,
            TripRepository tripRepository,
            SeatRepository seatRepository) {
        this.bookingRepository = bookingRepository;
        this.tripRepository = tripRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (bookingRepository.count() > 0) {
            System.out.println("✅ Bookings already seeded.");
            return;
        }

        List<Trip> trips = tripRepository.findAll();
        List<Seat> seats = seatRepository.findAll();

        if (trips.isEmpty() || seats.isEmpty()) {
            System.out.println("⚠️ Cannot seed Bookings: No Trips or Seats found.");
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        List<Booking> bookings = new ArrayList<>();

        // Only users that are Employees (as per your UserSeeder mapping)
        long[] employeeUserIds = { 1, 2, 5, 6, 8, 10 };

        // Booking for rahul (userId 1)
        bookings.add(new Booking(null, 1L, trips.get(0), seats.get(0), now, LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(1),
                BookingStatus.CONFIRMED, now, now));
        bookings.add(new Booking(null, 1L, trips.get(1), seats.get(1), now, LocalDate.now().plusDays(2),
                LocalDate.now().plusDays(2),
                BookingStatus.CONFIRMED, now, now));

        // Booking for bharat (userId 2)
        bookings.add(new Booking(null, 2L, trips.get(2), seats.get(2), now, LocalDate.now().plusDays(3),
                LocalDate.now().plusDays(3),
                BookingStatus.CONFIRMED, now, now));
        bookings.add(new Booking(null, 2L, trips.get(3), seats.get(3), now, LocalDate.now().plusDays(4),
                LocalDate.now().plusDays(4),
                BookingStatus.CONFIRMED, now, now));

        // Booking for raj (userId 5)
        bookings.add(new Booking(null, 5L, trips.get(4), seats.get(4), now, LocalDate.now().plusDays(5),
                LocalDate.now().plusDays(5),
                BookingStatus.CONFIRMED, now, now));
        bookings.add(new Booking(null, 5L, trips.get(5), seats.get(5), now, LocalDate.now().plusDays(6),
                LocalDate.now().plusDays(6),
                BookingStatus.CONFIRMED, now, now));

        // Booking for neha (userId 6)
        bookings.add(new Booking(null, 6L, trips.get(6), seats.get(6), now, LocalDate.now().plusDays(7),
                LocalDate.now().plusDays(7),
                BookingStatus.CONFIRMED, now, now));
        bookings.add(new Booking(null, 6L, trips.get(7), seats.get(7), now, LocalDate.now().plusDays(8),
                LocalDate.now().plusDays(8),
                BookingStatus.CONFIRMED, now, now));

        // Booking for kavita (userId 8)
        bookings.add(new Booking(null, 8L, trips.get(8), seats.get(8), now, LocalDate.now().plusDays(9),
                LocalDate.now().plusDays(9),
                BookingStatus.CONFIRMED, now, now));
        bookings.add(new Booking(null, 8L, trips.get(9), seats.get(9), now, LocalDate.now().plusDays(10),
                LocalDate.now().plusDays(10),
                BookingStatus.CONFIRMED, now, now));

        // Booking for pooja (userId 10)
        bookings.add(new Booking(null, 10L, trips.get(10), seats.get(10), now, LocalDate.now().plusDays(11),
                LocalDate.now().plusDays(11),
                BookingStatus.CONFIRMED, now, now));
        bookings.add(new Booking(null, 10L, trips.get(11), seats.get(11), now, LocalDate.now().plusDays(12),
                LocalDate.now().plusDays(12),
                BookingStatus.CONFIRMED, now, now));

        bookingRepository.saveAll(bookings);
        System.out.println("✅ Bookings seeded with " + bookings.size() + " records.");
    }
}
