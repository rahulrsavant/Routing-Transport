package com.ideabotkey.transportrouting.seeders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ideabotkey.transportrouting.TransportroutingApplication;
import com.ideabotkey.transportrouting.model.Driver;
import com.ideabotkey.transportrouting.model.Route;
import com.ideabotkey.transportrouting.model.Trip;
import com.ideabotkey.transportrouting.model.Vehicle;
import com.ideabotkey.transportrouting.repository.DriverRepository;
import com.ideabotkey.transportrouting.repository.RouteRepository;
import com.ideabotkey.transportrouting.repository.TripRepository;
import com.ideabotkey.transportrouting.repository.VehicleRepository;

@Component
public class TripSeeder implements CommandLineRunner {

    private final TripRepository tripRepository;
    private final RouteRepository routeRepository;
    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;

    public TripSeeder(
            TripRepository tripRepository,
            RouteRepository routeRepository,
            VehicleRepository vehicleRepository,
            DriverRepository driverRepository) {
        this.tripRepository = tripRepository;
        this.routeRepository = routeRepository;
        this.vehicleRepository = vehicleRepository;
        this.driverRepository = driverRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (tripRepository.count() > 0) {
            System.out.println("✅ Trips already seeded.");
            return;
        }

        List<Route> routes = routeRepository.findAll();
        List<Vehicle> vehicles = vehicleRepository.findAll();
        List<Driver> drivers = driverRepository.findAll();

        if (routes == null || routes.size() < 10 || vehicles == null || vehicles.size() < 10
                || drivers == null || drivers.size() < 50) {
            System.out.println("⚠️ Cannot seed Trips: Missing Routes, Vehicles, or Drivers.");
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        List<Trip> trips = new ArrayList<>();

        // 20 manual trips (edit as needed)
        trips.add(new Trip(null, routes.get(0), vehicles.get(0), drivers.get(0), LocalDate.now().plusDays(0), "07:00",
                "09:00", "SCHEDULED", now, now));
        trips.add(new Trip(null, routes.get(1), vehicles.get(1), drivers.get(1), LocalDate.now().plusDays(1), "08:00",
                "10:00", "SCHEDULED", now, now));
        trips.add(new Trip(null, routes.get(2), vehicles.get(2), drivers.get(2), LocalDate.now().plusDays(2), "09:00",
                "11:00", "ACTIVE", now, now));
        trips.add(new Trip(null, routes.get(3), vehicles.get(3), drivers.get(3), LocalDate.now().plusDays(3), "10:00",
                "12:00", "SCHEDULED", now, now));
        trips.add(new Trip(null, routes.get(4), vehicles.get(4), drivers.get(4), LocalDate.now().plusDays(4), "11:00",
                "13:00", "ACTIVE", now, now));
        trips.add(new Trip(null, routes.get(5), vehicles.get(5), drivers.get(5), LocalDate.now().plusDays(5), "12:00",
                "14:00", "SCHEDULED", now, now));
        trips.add(new Trip(null, routes.get(6), vehicles.get(6), drivers.get(6), LocalDate.now().plusDays(6), "13:00",
                "15:00", "SCHEDULED", now, now));
        trips.add(new Trip(null, routes.get(7), vehicles.get(7), drivers.get(7), LocalDate.now().plusDays(7), "14:00",
                "16:00", "ACTIVE", now, now));
        trips.add(new Trip(null, routes.get(8), vehicles.get(8), drivers.get(8), LocalDate.now().plusDays(8), "15:00",
                "17:00", "CANCELLED", now, now));
        trips.add(new Trip(null, routes.get(9), vehicles.get(9), drivers.get(9), LocalDate.now().plusDays(9), "16:00",
                "18:00", "SCHEDULED", now, now));
        trips.add(new Trip(null, routes.get(0), vehicles.get(1), drivers.get(10), LocalDate.now().plusDays(10), "17:00",
                "19:00", "SCHEDULED", now, now));
        trips.add(new Trip(null, routes.get(1), vehicles.get(2), drivers.get(11), LocalDate.now().plusDays(11), "18:00",
                "20:00", "ACTIVE", now, now));
        trips.add(new Trip(null, routes.get(2), vehicles.get(3), drivers.get(12), LocalDate.now().plusDays(12), "06:00",
                "08:00", "SCHEDULED", now, now));
        trips.add(new Trip(null, routes.get(3), vehicles.get(4), drivers.get(13), LocalDate.now().plusDays(13), "07:30",
                "09:30", "SCHEDULED", now, now));
        trips.add(new Trip(null, routes.get(4), vehicles.get(5), drivers.get(14), LocalDate.now().plusDays(14), "08:30",
                "10:30", "ACTIVE", now, now));
        trips.add(new Trip(null, routes.get(5), vehicles.get(6), drivers.get(15), LocalDate.now().plusDays(15), "09:30",
                "11:30", "SCHEDULED", now, now));
        trips.add(new Trip(null, routes.get(6), vehicles.get(7), drivers.get(16), LocalDate.now().plusDays(16), "10:30",
                "12:30", "SCHEDULED", now, now));
        trips.add(new Trip(null, routes.get(7), vehicles.get(8), drivers.get(17), LocalDate.now().plusDays(17), "11:30",
                "13:30", "CANCELLED", now, now));
        trips.add(new Trip(null, routes.get(8), vehicles.get(9), drivers.get(18), LocalDate.now().plusDays(18), "12:30",
                "14:30", "SCHEDULED", now, now));
        trips.add(new Trip(null, routes.get(9), vehicles.get(0), drivers.get(19), LocalDate.now().plusDays(19), "13:30",
                "15:30", "ACTIVE", now, now));
        trips.add(new Trip(null, routes.get(1), vehicles.get(3), drivers.get(20), LocalDate.now().plusDays(20), "14:00",
                "16:00", "SCHEDULED", now, now));
        trips.add(new Trip(null, routes.get(2), vehicles.get(5), drivers.get(21), LocalDate.now().plusDays(21), "15:00",
                "17:00", "ACTIVE", now, now));
        trips.add(new Trip(null, routes.get(3), vehicles.get(7), drivers.get(22), LocalDate.now().plusDays(22), "16:00",
                "18:00", "CANCELLED", now, now));
        trips.add(new Trip(null, routes.get(4), vehicles.get(9), drivers.get(23), LocalDate.now().plusDays(23), "06:30",
                "08:30", "SCHEDULED", now, now));
        trips.add(new Trip(null, routes.get(5), vehicles.get(2), drivers.get(24), LocalDate.now().plusDays(24), "07:15",
                "09:15", "ACTIVE", now, now));
        trips.add(new Trip(null, routes.get(6), vehicles.get(4), drivers.get(25), LocalDate.now().plusDays(25), "08:45",
                "10:45", "SCHEDULED", now, now));
        trips.add(new Trip(null, routes.get(7), vehicles.get(6), drivers.get(26), LocalDate.now().plusDays(26), "09:45",
                "11:45", "CANCELLED", now, now));
        trips.add(new Trip(null, routes.get(8), vehicles.get(8), drivers.get(27), LocalDate.now().plusDays(27), "10:15",
                "12:15", "SCHEDULED", now, now));
        trips.add(new Trip(null, routes.get(0), vehicles.get(1), drivers.get(28), LocalDate.now().plusDays(28), "11:00",
                "13:00", "ACTIVE", now, now));
        trips.add(new Trip(null, routes.get(1), vehicles.get(3), drivers.get(29), LocalDate.now().plusDays(29), "12:30",
                "14:30", "SCHEDULED", now, now));
        trips.add(new Trip(null, routes.get(2), vehicles.get(5), drivers.get(30), LocalDate.now().plusDays(30), "13:00",
                "15:00", "ACTIVE", now, now));
        trips.add(new Trip(null, routes.get(3), vehicles.get(7), drivers.get(31), LocalDate.now().plusDays(31), "14:15",
                "16:15", "CANCELLED", now, now));
        trips.add(new Trip(null, routes.get(4), vehicles.get(9), drivers.get(32), LocalDate.now().plusDays(32), "15:30",
                "17:30", "SCHEDULED", now, now));
        trips.add(new Trip(null, routes.get(5), vehicles.get(2), drivers.get(33), LocalDate.now().plusDays(33), "16:45",
                "18:45", "ACTIVE", now, now));
        trips.add(new Trip(null, routes.get(6), vehicles.get(4), drivers.get(34), LocalDate.now().plusDays(34), "06:00",
                "08:00", "SCHEDULED", now, now));
        trips.add(new Trip(null, routes.get(7), vehicles.get(6), drivers.get(35), LocalDate.now().plusDays(35), "07:00",
                "09:00", "ACTIVE", now, now));
        trips.add(new Trip(null, routes.get(8), vehicles.get(8), drivers.get(36), LocalDate.now().plusDays(36), "08:00",
                "10:00", "CANCELLED", now, now));
        trips.add(new Trip(null, routes.get(9), vehicles.get(0), drivers.get(37), LocalDate.now().plusDays(37), "09:30",
                "11:30", "SCHEDULED", now, now));
        trips.add(new Trip(null, routes.get(0), vehicles.get(2), drivers.get(38), LocalDate.now().plusDays(38), "10:15",
                "12:15", "ACTIVE", now, now));
        trips.add(new Trip(null, routes.get(1), vehicles.get(4), drivers.get(39), LocalDate.now().plusDays(39), "11:30",
                "13:30", "SCHEDULED", now, now));
        trips.add(new Trip(null, routes.get(2), vehicles.get(6), drivers.get(40), LocalDate.now().plusDays(40), "12:45",
                "14:45", "ACTIVE", now, now));
        trips.add(new Trip(null, routes.get(3), vehicles.get(8), drivers.get(41), LocalDate.now().plusDays(41), "13:45",
                "15:45", "CANCELLED", now, now));
        trips.add(new Trip(null, routes.get(4), vehicles.get(1), drivers.get(42), LocalDate.now().plusDays(42), "14:30",
                "16:30", "SCHEDULED", now, now));
        trips.add(new Trip(null, routes.get(5), vehicles.get(3), drivers.get(43), LocalDate.now().plusDays(43), "15:15",
                "17:15", "ACTIVE", now, now));
        trips.add(new Trip(null, routes.get(6), vehicles.get(5), drivers.get(44), LocalDate.now().plusDays(44), "16:30",
                "18:30", "SCHEDULED", now, now));
        trips.add(new Trip(null, routes.get(7), vehicles.get(7), drivers.get(45), LocalDate.now().plusDays(45), "06:30",
                "08:30", "ACTIVE", now, now));
        trips.add(new Trip(null, routes.get(8), vehicles.get(9), drivers.get(46), LocalDate.now().plusDays(46), "07:45",
                "09:45", "CANCELLED", now, now));
        trips.add(new Trip(null, routes.get(9), vehicles.get(2), drivers.get(47), LocalDate.now().plusDays(47), "08:30",
                "10:30", "SCHEDULED", now, now));
        trips.add(new Trip(null, routes.get(0), vehicles.get(4), drivers.get(48), LocalDate.now().plusDays(48), "09:15",
                "11:15", "ACTIVE", now, now));
        trips.add(new Trip(null, routes.get(1), vehicles.get(6), drivers.get(49), LocalDate.now().plusDays(49), "10:45",
                "12:45", "SCHEDULED", now, now));

        tripRepository.saveAll(trips);
        System.out.println("✅ Trips seeded with " + trips.size() + " records.");
    }

public static void main(String[] args) {
    org.springframework.boot.SpringApplication app = new org.springframework.boot.SpringApplication(TransportroutingApplication.class);
    var context = app.run(args);

    // If manual seeding is requested:
    if (args.length > 0 && args[0].equalsIgnoreCase("seed-trips")) {
        TripSeeder seeder = context.getBean(TripSeeder.class);
        try {
            seeder.run();
            System.out.println("✅ Manual TripSeeder run completed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0); // Exit after seeding!
    }
}

}


