package com.ideabotkey.transportrouting.seeders;

import com.ideabotkey.transportrouting.model.Permission;
import com.ideabotkey.transportrouting.model.Role;
import com.ideabotkey.transportrouting.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.List;

@Component
public class RoleSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() > 0) {
            System.out.println("✅ Roles already exist, skipping RoleSeeder.");
            return;
        }

        System.out.println("Seeding roles...");

        Role admin = new Role();
        admin.setName("ADMIN");
        admin.setDescription("Administrator with all permissions");
        admin.setPermissions(EnumSet.allOf(Permission.class));

        Role driver = new Role();
        driver.setName("DRIVER");
        driver.setDescription("Driver role");
        driver.setPermissions(EnumSet.of(Permission.VIEW_TRIP, Permission.VIEW_SEAT, Permission.BOOK_SEAT));

        Role vehicleOwner = new Role();
        vehicleOwner.setName("VEHICLE_OWNER");
        vehicleOwner.setDescription("Vehicle owner role");
        vehicleOwner.setPermissions(EnumSet.of(Permission.VIEW_VEHICLE, Permission.CREATE_VEHICLE));

        Role companyOwner = new Role();
        companyOwner.setName("COMPANY_OWNER");
        companyOwner.setDescription("Company owner role");
        companyOwner.setPermissions(EnumSet.of(Permission.VIEW_COMPANY, Permission.UPDATE_COMPANY));

        roleRepository.saveAll(List.of(admin, driver, vehicleOwner, companyOwner));

        System.out.println("✅ Roles seeded successfully");
    }
}
