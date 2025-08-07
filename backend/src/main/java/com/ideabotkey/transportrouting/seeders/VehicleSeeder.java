package com.ideabotkey.transportrouting.seeders;

import com.ideabotkey.transportrouting.model.Vehicle;
import com.ideabotkey.transportrouting.model.VehicleOwner;
import com.ideabotkey.transportrouting.repository.VehicleOwnerRepository;
import com.ideabotkey.transportrouting.repository.VehicleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class VehicleSeeder implements CommandLineRunner {

    private final VehicleRepository vehicleRepository;
    private final VehicleOwnerRepository vehicleOwnerRepository;

    public VehicleSeeder(VehicleRepository vehicleRepository, VehicleOwnerRepository vehicleOwnerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleOwnerRepository = vehicleOwnerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (vehicleRepository.count() == 0) {
            List<VehicleOwner> owners = vehicleOwnerRepository.findAll();
            if (owners.isEmpty()) {
                System.out.println("⚠️ Cannot seed Vehicles: no VehicleOwners found.");
                return;
            }

            LocalDateTime now = LocalDateTime.now();
            List<Vehicle> vehicles = new ArrayList<>();

            vehicles.add(new Vehicle(owners.get(0), "Mini Bus", "MH12AB1234", 20, "Force Traveller", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(0), "Truck", "MH14CD5678", 2, "Tata LPT", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(1), "Cargo Van", "MH12EF9101", 10, "Mahindra Supro", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(1), "Coach", "MH14GH2345", 50, "Volvo 9700", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(2), "Pickup", "MH12IJ6789", 5, "Isuzu D-Max", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(2), "Van", "MH14KL3456", 15, "Maruti Eeco", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(3), "Mini Bus", "MH12MN7890", 20, "Ashok Leyland Lynx", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(3), "Truck", "MH14OP1234", 2, "BharatBenz 1217C", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(4), "Cargo Van", "MH12QR5678", 10, "Force Trax", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(4), "Van", "MH14ST9101", 15, "Tempo Traveller", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(5), "Mini Bus", "MH12UV1122", 25, "Eicher Skyline", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(5), "Truck", "MH14WX3344", 2, "Tata Ultra", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(6), "Coach", "MH12YZ5566", 45, "Scania Metrolink", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(6), "Van", "MH14GH7788", 14, "Force Traveller", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(7), "Pickup", "MH12JK9900", 5, "Mahindra Bolero Pickup", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(7), "Cargo Van", "MH14LM2233", 11, "Tata Ace", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(8), "Mini Bus", "MH12NO4455", 18, "Ashok Leyland Sunshine", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(8), "Van", "MH14PQ6677", 12, "Winger School Van", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(9), "Truck", "MH12RS8899", 3, "Eicher Pro 1110", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(9), "Coach", "MH14TU1011", 52, "Mercedes-Benz MultiAxle", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(0), "Mini Bus", "MH12AB1414", 24, "Force Traveller", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(1), "Cargo Van", "MH14CD2525", 9, "Mahindra Maxximo", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(2), "Van", "MH12EF3636", 17, "Tata Magic", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(3), "Truck", "MH14GH4747", 2, "BharatBenz 1015R", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(4), "Mini Bus", "MH12IJ5858", 21, "Ashok Leyland Oyster", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(5), "Coach", "MH14KL6699", 49, "Volvo B11R", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(6), "Truck", "MH12MN7788", 3, "Tata Signa", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(7), "Pickup", "MH14OP9900", 5, "Tata Yodha", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(8), "Van", "MH12QR2244", 12, "Maruti Omni", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(9), "Mini Bus", "MH14ST3355", 19, "Mahindra Excelo", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(0), "Cargo Van", "MH12UV5566", 9, "Mahindra Jeeto", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(1), "Coach", "MH14WX7788", 47, "Ashok Leyland Falcon", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(2), "Van", "MH12YZ9900", 11, "Force Urbania", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(3), "Truck", "MH14GH1199", 2, "Tata Intra V50", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(4), "Mini Bus", "MH12IJ8822", 20, "Eicher Starline", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(5), "Cargo Van", "MH14KL3311", 10, "Tata Winger Cargo", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(6), "Mini Bus", "MH12MN6644", 23, "Ashok Leyland Mitr", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(7), "Coach", "MH14OP7755", 48, "Scania F320", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(8), "Truck", "MH12QR4422", 2, "Eicher Pro 2049", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(9), "Van", "MH14ST6688", 15, "Force Traveller 3050", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(0), "Mini Bus", "MH12AB1626", 20, "Force Traveller", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(1), "Pickup", "MH14CD3434", 5, "Isuzu D-Max", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(2), "Van", "MH12EF7474", 13, "Maruti Eeco", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(3), "Truck", "MH14GH5656", 3, "BharatBenz 1617R", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(4), "Mini Bus", "MH12IJ7878", 22, "Ashok Leyland Mitr", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(5), "Cargo Van", "MH14KL1313", 10, "Tata Ace Zip", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(6), "Van", "MH12MN3535", 14, "Tata Magic Express", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(7), "Truck", "MH14OP9696", 4, "Mahindra Furio", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(8), "Coach", "MH12QR5757", 40, "Volvo 9400", "ACTIVE"));
            vehicles.add(new Vehicle(owners.get(9), "Pickup", "MH14ST9797", 5, "Mahindra Bolero Maxi Truck", "ACTIVE"));

            vehicles.forEach(v -> {
                v.setCreatedAt(now);
                v.setUpdatedAt(now);
            });

            vehicleRepository.saveAll(vehicles);
            System.out.println("✅ Seeded 50 Vehicles");
        }
    }
}
