package com.ideabotkey.transportrouting.seeders;

import com.ideabotkey.transportrouting.model.Driver;
import com.ideabotkey.transportrouting.model.Vehicle;
import com.ideabotkey.transportrouting.repository.DriverRepository;
import com.ideabotkey.transportrouting.repository.VehicleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DriverSeeder implements CommandLineRunner {

    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;

    public DriverSeeder(DriverRepository driverRepository, VehicleRepository vehicleRepository) {
        this.driverRepository = driverRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (driverRepository.count() == 0) {
            List<Vehicle> vehicles = vehicleRepository.findAll();
            if (vehicles.isEmpty()) {
                System.out.println("⚠️ Cannot seed Drivers: no Vehicles found.");
                return;
            }

            LocalDateTime now = LocalDateTime.now();
            List<Driver> drivers = new ArrayList<>();

            drivers.add(new Driver("Amit Pawar", "+91-9876543201", "MH12DL001", "Chinchwad",
                    vehicles.get(0 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Suresh Jadhav", "+91-9876543202", "MH14DL002", "Pimpri",
                    vehicles.get(1 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Rohit Patil", "+91-9876543203", "MH12DL003", "Akurdi",
                    vehicles.get(2 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Sneha Kadam", "+91-9876543204", "MH14DL004", "Nigdi",
                    vehicles.get(3 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Rajesh Shinde", "+91-9876543205", "MH12DL005", "Bhosari",
                    vehicles.get(4 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Neha Joshi", "+91-9876543206", "MH14DL006", "Dapodi",
                    vehicles.get(5 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Ankit Kumar", "+91-9876543207", "MH12DL007", "Kalewadi",
                    vehicles.get(6 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Kavita Reddy", "+91-9876543208", "MH14DL008", "Thergaon",
                    vehicles.get(7 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Vikas Nair", "+91-9876543209", "MH12DL009", "Wakad",
                    vehicles.get(8 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Pooja Mehra", "+91-9876543210", "MH14DL010", "Sangvi",
                    vehicles.get(9 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Shyam Patil", "+91-9876543211", "MH12DL011", "Pimple Saudagar",
                    vehicles.get(10 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Meena Kulkarni", "+91-9876543212", "MH14DL012", "Pimple Gurav",
                    vehicles.get(11 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Swapnil Deshmukh", "+91-9876543213", "MH12DL013", "Ravet",
                    vehicles.get(12 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Sunita Gawade", "+91-9876543214", "MH14DL014", "Moshi",
                    vehicles.get(13 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Anil More", "+91-9876543215", "MH12DL015", "Dehu Road",
                    vehicles.get(14 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Nisha Yadav", "+91-9876543216", "MH14DL016", "Talegaon",
                    vehicles.get(15 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Sandip Gaikwad", "+91-9876543217", "MH12DL017", "Tathawade",
                    vehicles.get(16 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Vandana Joshi", "+91-9876543218", "MH14DL018", "Bopodi",
                    vehicles.get(17 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Ramesh Kamble", "+91-9876543219", "MH12DL019", "Phugewadi",
                    vehicles.get(18 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Arjun Nikam", "+91-9876543220", "MH14DL020", "Kasariwdi",
                    vehicles.get(19 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Madhuri Shitole", "+91-9876543221", "MH12DL021", "Chikhali",
                    vehicles.get(20 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Ashok Sathe", "+91-9876543222", "MH14DL022", "Charholi",
                    vehicles.get(21 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Smita Khot", "+91-9876543223", "MH12DL023", "Alandi",
                    vehicles.get(22 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Milind Bhujbal", "+91-9876543224", "MH14DL024", "Kasarwadi",
                    vehicles.get(23 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Priti Salunke", "+91-9876543225", "MH12DL025", "Punawale",
                    vehicles.get(24 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Ganesh Pawar", "+91-9876543226", "MH14DL026", "Dighi",
                    vehicles.get(25 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Shital Thorat", "+91-9876543227", "MH12DL027", "Talwade",
                    vehicles.get(26 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Omkar Dange", "+91-9876543228", "MH14DL028", "Ravet",
                    vehicles.get(27 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Vaishali Gharat", "+91-9876543229", "MH12DL029", "Aundh",
                    vehicles.get(28 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Yogesh Kharat", "+91-9876543230", "MH14DL030", "Baner",
                    vehicles.get(29 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Ujwala Chavan", "+91-9876543231", "MH12DL031", "Balewadi",
                    vehicles.get(30 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Kiran Dhamale", "+91-9876543232", "MH14DL032", "Pashan",
                    vehicles.get(31 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Harshal Landge", "+91-9876543233", "MH12DL033", "Sangvi",
                    vehicles.get(32 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Sangita Bhosale", "+91-9876543234", "MH14DL034", "Kalewadi",
                    vehicles.get(33 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Shrikant Lokhande", "+91-9876543235", "MH12DL035", "Thergaon",
                    vehicles.get(34 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Sarika Desai", "+91-9876543236", "MH14DL036", "Wakad",
                    vehicles.get(35 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Hemant Mane", "+91-9876543237", "MH12DL037", "Sangvi",
                    vehicles.get(36 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Pramod Tapkir", "+91-9876543238", "MH14DL038", "Nigdi",
                    vehicles.get(37 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Tejaswini Bhand", "+91-9876543239", "MH12DL039", "Bhosari",
                    vehicles.get(38 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Prashant Salve", "+91-9876543240", "MH14DL040", "Pimpri",
                    vehicles.get(39 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Anuja Ingle", "+91-9876543241", "MH12DL041", "Chinchwad",
                    vehicles.get(40 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Vishal Pimple", "+91-9876543242", "MH14DL042", "Moshi",
                    vehicles.get(41 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Rupali Kendre", "+91-9876543243", "MH12DL043", "Ravet",
                    vehicles.get(42 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Kalyani Gokhale", "+91-9876543244", "MH14DL044", "Kasariwdi",
                    vehicles.get(43 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Vikram Dhore", "+91-9876543245", "MH12DL045", "Pimple Saudagar",
                    vehicles.get(44 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Sanjay Kale", "+91-9876543246", "MH14DL046", "Nigdi",
                    vehicles.get(45 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Sayali Pingale", "+91-9876543247", "MH12DL047", "Kalewadi",
                    vehicles.get(46 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Sachin Patole", "+91-9876543248", "MH14DL048", "Dapodi",
                    vehicles.get(47 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Ketki Ambekar", "+91-9876543249", "MH12DL049", "Chikhali",
                    vehicles.get(48 % vehicles.size()), "ACTIVE"));
            drivers.add(new Driver("Sagar Shinde", "+91-9876543250", "MH14DL050", "Akurdi",
                    vehicles.get(49 % vehicles.size()), "ACTIVE"));

            drivers.forEach(d -> {
                d.setCreatedAt(now);
                d.setUpdatedAt(now);
            });

            driverRepository.saveAll(drivers);
            System.out.println("✅ Seeded 50 Drivers (Manual, No Loops, MH12/MH14, PCMC/Pune Areas)");
        }
    }
}
