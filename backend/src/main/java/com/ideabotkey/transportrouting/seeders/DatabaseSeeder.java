
package com.ideabotkey.transportrouting.seeders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev") // Only run in dev
public class DatabaseSeeder implements CommandLineRunner {

    private final CompanySeeder companySeeder;
    private final VehicleOwnerSeeder vehicleOwnerSeeder;
    private final VehicleSeeder vehicleSeeder;
    private final ContractSeeder contractSeeder;
    private final RouteSeeder routeSeeder;
    private final DriverSeeder driverSeeder;
    private final SeatSeeder seatSeeder;
    private final EmployeeSeeder employeeSeeder;
    private final PermissionSeeder permissionSeeder;
    private final RoleSeeder roleSeeder;
    private final UserSeeder userSeeder;
    private final TripSeeder tripSeeder;
    private final TripEmployeeSeeder tripEmployeeSeeder;
    private final BookingSeeder bookingSeeder;

    public DatabaseSeeder(
            CompanySeeder companySeeder,
            VehicleOwnerSeeder vehicleOwnerSeeder,
            VehicleSeeder vehicleSeeder,
            ContractSeeder contractSeeder,
            RouteSeeder routeSeeder,
            DriverSeeder driverSeeder,
            SeatSeeder seatSeeder,
            EmployeeSeeder employeeSeeder,
            PermissionSeeder permissionSeeder,
            RoleSeeder roleSeeder,
            UserSeeder userSeeder,
            TripSeeder tripSeeder,
            TripEmployeeSeeder tripEmployeeSeeder,
            BookingSeeder bookingSeeder

    ) {
        this.companySeeder = companySeeder;
        this.vehicleOwnerSeeder = vehicleOwnerSeeder;
        this.vehicleSeeder = vehicleSeeder;
        this.contractSeeder = contractSeeder;
        this.routeSeeder = routeSeeder;
        this.driverSeeder = driverSeeder;
        this.seatSeeder = seatSeeder;
        this.employeeSeeder = employeeSeeder;
        this.permissionSeeder = permissionSeeder;
        this.roleSeeder = roleSeeder;
        this.userSeeder = userSeeder;
        this.tripSeeder = tripSeeder;
        this.tripEmployeeSeeder = tripEmployeeSeeder;
        this.bookingSeeder = bookingSeeder;
    }

    @Override
    public void run(String... args) {
        System.out.println("🔵 Starting master DatabaseSeeder...");

        // Foundational first
        runSeeder(companySeeder);
        runSeeder(vehicleOwnerSeeder);
        runSeeder(vehicleSeeder); // Needed for drivers
        runSeeder(contractSeeder); // Needed for routes

        // Now seed routes, drivers, seats
        runSeeder(routeSeeder); // Needs contracts
        runSeeder(driverSeeder); // Needs vehicles
        runSeeder(seatSeeder); // Needs vehicles

        // Employees and permissions
        runSeeder(employeeSeeder);
        runSeeder(permissionSeeder);
        runSeeder(roleSeeder);

        // Users (needs roles)
        runSeeder(userSeeder);

        // Trips depend on drivers/routes/vehicles
        runSeeder(tripSeeder);
        runSeeder(tripEmployeeSeeder);

        // Bookings depend on trips and seats
        runSeeder(bookingSeeder);

        System.out.println("✅ All seeders completed successfully!");
    }

    private void runSeeder(CommandLineRunner seeder) {
        try {
            seeder.run();
        } catch (Exception e) {
            System.out.println(
                    "❌ " + seeder.getClass().getSimpleName() + " failed: " + e.getMessage());
        }
    }


}

