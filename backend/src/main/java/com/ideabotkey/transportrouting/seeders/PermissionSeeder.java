package com.ideabotkey.transportrouting.seeders;

import com.ideabotkey.transportrouting.model.Permission;
import com.ideabotkey.transportrouting.model.PermissionEntity;
import com.ideabotkey.transportrouting.repository.PermissionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class PermissionSeeder implements CommandLineRunner {

    private final PermissionRepository permissionRepository;

    public PermissionSeeder(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (permissionRepository.count() > 0) {
            System.out.println("✅ Permissions already exist, skipping PermissionSeeder.");
            return;
        }

        System.out.println("Seeding permissions...");

        int count = 0;
        for (Permission p : Permission.values()) {
            if (!permissionRepository.existsById(p.getId())) {
                permissionRepository.save(new PermissionEntity(p.getCode(), p.getDescription()));
                count++;
            }
        }

        System.out.println("✅ Permissions seeded: " + count);
    }
}
