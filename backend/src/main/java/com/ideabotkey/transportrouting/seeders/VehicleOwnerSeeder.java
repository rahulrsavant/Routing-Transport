package com.ideabotkey.transportrouting.seeders;

import com.ideabotkey.transportrouting.model.VehicleOwner;
import com.ideabotkey.transportrouting.repository.VehicleOwnerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class VehicleOwnerSeeder implements CommandLineRunner {

    private final VehicleOwnerRepository vehicleOwnerRepository;

    public VehicleOwnerSeeder(VehicleOwnerRepository vehicleOwnerRepository) {
        this.vehicleOwnerRepository = vehicleOwnerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (vehicleOwnerRepository.count() == 0) {
            LocalDateTime now = LocalDateTime.now();

            List<VehicleOwner> owners = List.of(
                    new VehicleOwner("Rajesh Transport Services", "rajesh@transport.com", "+91-9876543210",
                            "12 MG Road, Bengaluru", "ACTIVE"),
                    new VehicleOwner("Patel Fleet Management", "patel@fleet.com", "+91-9123456789",
                            "34 Park Street, Mumbai", "ACTIVE"),
                    new VehicleOwner("Verma Vehicle Owners", "verma@vehicles.com", "+91-9988776655",
                            "56 Anna Salai, Chennai", "ACTIVE"),
                    new VehicleOwner("Singh Logistics", "singh@logistics.com", "+91-9876123456",
                            "78 Connaught Place, Delhi", "ACTIVE"),
                    new VehicleOwner("Joshi Movers", "joshi@movers.com", "+91-9123123123", "90 Ashram Road, Ahmedabad",
                            "ACTIVE"),
                    new VehicleOwner("PCMC Fleet Services", "fleet@pcmc.com", "+91-9881122334",
                            "21 Old Mumbai Pune Road, Pimpri, Pune", "ACTIVE"),
                    new VehicleOwner("Pune Express Logistics", "puneexp@express.com", "+91-9090090909",
                            "45 Fugewadi, Pune", "ACTIVE"),
                    new VehicleOwner("Chinchwad Vehicle Rentals", "cvr@chinchwad.com", "+91-9850607070",
                            "67 Chinchwad Gaon, Pune", "ACTIVE"),
                    new VehicleOwner("Bhosari Freight Carriers", "bfc@bhosari.com", "+91-9823456789",
                            "89 MIDC, Bhosari, Pune", "ACTIVE"),
                    new VehicleOwner("Wakad Highway Transports", "wakadht@transport.com", "+91-9922554477",
                            "101 Wakad, Pune", "ACTIVE"));

            owners.forEach(o -> {
                o.setCreatedAt(now);
                o.setUpdatedAt(now);
            });

            vehicleOwnerRepository.saveAll(owners);
            System.out.println("✅ Seeded 10 Vehicle Owners (PCMC & Metro cities)");
        }
    }
}
