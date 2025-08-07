package com.ideabotkey.transportrouting.seeders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ideabotkey.transportrouting.model.Employee;
import com.ideabotkey.transportrouting.model.Trip;
import com.ideabotkey.transportrouting.model.TripEmployee;
import com.ideabotkey.transportrouting.repository.EmployeeRepository;
import com.ideabotkey.transportrouting.repository.TripEmployeeRepository;
import com.ideabotkey.transportrouting.repository.TripRepository;

@Component
public class TripEmployeeSeeder implements CommandLineRunner {

    private final TripEmployeeRepository tripEmployeeRepository;
    private final TripRepository tripRepository;
    private final EmployeeRepository employeeRepository;

    public TripEmployeeSeeder(
            TripEmployeeRepository tripEmployeeRepository,
            TripRepository tripRepository,
            EmployeeRepository employeeRepository) {
        this.tripEmployeeRepository = tripEmployeeRepository;
        this.tripRepository = tripRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (tripEmployeeRepository.count() > 0) {
            System.out.println("✅ TripEmployees already seeded.");
            return;
        }

        List<Trip> trips = tripRepository.findAll();
        List<Employee> employees = employeeRepository.findAll();

        if (trips == null || trips.isEmpty() || employees == null || employees.isEmpty()) {
            System.out.println("⚠️ Cannot seed TripEmployees: Missing Trips or Employees.");
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        List<TripEmployee> tripEmployees = new ArrayList<>();
        Random random = new Random();

        // Create 200 TripEmployee links (customize as needed)
        int desiredLinks = 200;
        for (int i = 0; i < desiredLinks; i++) {
            Trip trip = trips.get(random.nextInt(trips.size()));
            Employee employee = employees.get(random.nextInt(employees.size()));

            TripEmployee te = new TripEmployee();
            te.setTrip(trip);
            te.setEmployee(employee);
            te.setPickUpPoint("Point #" + (random.nextInt(100) + 1));
            te.setDropOffPoint("Destination #" + (random.nextInt(100) + 1));
            te.setStatus("ACTIVE");
            te.setCreatedAt(now);
            te.setUpdatedAt(now);

            tripEmployees.add(te);
        }

        tripEmployeeRepository.saveAll(tripEmployees);
        System.out.println("✅ TripEmployees seeded with " + tripEmployees.size() + " records.");
    }
}
