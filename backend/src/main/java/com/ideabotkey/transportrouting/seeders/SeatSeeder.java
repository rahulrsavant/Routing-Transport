package com.ideabotkey.transportrouting.seeders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ideabotkey.transportrouting.model.Seat;
import com.ideabotkey.transportrouting.model.Vehicle;
import com.ideabotkey.transportrouting.model.SeatStatus; // <-- Import the enum!
import com.ideabotkey.transportrouting.repository.SeatRepository;
import com.ideabotkey.transportrouting.repository.VehicleRepository;

@Component
public class SeatSeeder implements CommandLineRunner {

    private final SeatRepository seatRepository;
    private final VehicleRepository vehicleRepository;

    public SeatSeeder(SeatRepository seatRepository, VehicleRepository vehicleRepository) {
        this.seatRepository = seatRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (seatRepository.count() > 0) {
            System.out.println("✅ Seats already seeded.");
            return;
        }

        List<Vehicle> vehicles = vehicleRepository.findAll();
        if (vehicles == null || vehicles.isEmpty()) {
            System.out.println("⚠️ Cannot seed Seats: No Vehicles found.");
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        List<Seat> seats = new ArrayList<>();

        Vehicle v1 = vehicles.get(0);
        Vehicle v2 = vehicles.size() > 1 ? vehicles.get(1) : vehicles.get(0);

        for (int row = 1; row <= 5; row++) {
            for (int col = 1; col <= 4; col++) {
                String seatNum = "R" + row + "S" + col;
                seats.add(new Seat(v1, row, seatNum, 100.0, SeatStatus.AVAILABLE)); // <--- Use Enum
            }
        }

        for (int row = 1; row <= 3; row++) {
            for (int col = 1; col <= 4; col++) {
                String seatNum = "R" + row + "S" + col;
                seats.add(new Seat(v2, row, seatNum, 120.0, SeatStatus.AVAILABLE)); // <--- Use Enum
            }
        }

        seats.forEach(s -> {
            s.setCreatedAt(now);
            s.setUpdatedAt(now);
        });

        seatRepository.saveAll(seats);
        System.out.println("✅ Seats seeded with " + seats.size() + " records.");
    }
}
