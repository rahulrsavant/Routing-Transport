// UPDATE contracts SET
//   contract_type = 'Annual',
//   created_at = '2025-08-01 17:30:00',
//   end_date = '2026-08-01',
//   start_date = '2025-08-01',
//   status = 'ACTIVE',
//   updated_at = '2025-08-01 17:30:00',
//   amount = 12000.00,
//   company_id = 1,
//   owner_id = 1,
//   vehicle_id = 1
// WHERE id = 1;

// UPDATE contracts SET
//   contract_type = 'Annual',
//   created_at = '2025-08-01 17:30:00',
//   end_date = '2026-08-01',
//   start_date = '2025-08-01',
//   status = 'ACTIVE',
//   updated_at = '2025-08-01 17:30:00',
//   amount = 13000.00,
//   company_id = 2,
//   owner_id = 2,
//   vehicle_id = 2
// WHERE id = 2;

// UPDATE contracts SET
//   contract_type = 'Annual',
//   created_at = '2025-08-01 17:30:00',
//   end_date = '2026-08-01',
//   start_date = '2025-08-01',
//   status = 'ACTIVE',
//   updated_at = '2025-08-01 17:30:00',
//   amount = 14000.00,
//   company_id = 3,
//   owner_id = 3,
//   vehicle_id = 3
// WHERE id = 3;

// UPDATE contracts SET
//   contract_type = 'Annual',
//   created_at = '2025-08-01 17:30:00',
//   end_date = '2026-08-01',
//   start_date = '2025-08-01',
//   status = 'ACTIVE',
//   updated_at = '2025-08-01 17:30:00',
//   amount = 15000.00,
//   company_id = 4,
//   owner_id = 4,
//   vehicle_id = 4
// WHERE id = 4;

// UPDATE contracts SET
//   contract_type = 'Annual',
//   created_at = '2025-08-01 17:30:00',
//   end_date = '2026-08-01',
//   start_date = '2025-08-01',
//   status = 'ACTIVE',
//   updated_at = '2025-08-01 17:30:00',
//   amount = 16000.00,
//   company_id = 5,
//   owner_id = 5,
//   vehicle_id = 5
// WHERE id = 5;

// UPDATE contracts SET
//   contract_type = 'Annual',
//   created_at = '2025-08-01 17:30:00',
//   end_date = '2026-08-01',
//   start_date = '2025-08-01',
//   status = 'ACTIVE',
//   updated_at = '2025-08-01 17:30:00',
//   amount = 17000.00,
//   company_id = 6,
//   owner_id = 1,
//   vehicle_id = 6
// WHERE id = 6;

// UPDATE contracts SET
//   contract_type = 'Annual',
//   created_at = '2025-08-01 17:30:00',
//   end_date = '2026-08-01',
//   start_date = '2025-08-01',
//   status = 'ACTIVE',
//   updated_at = '2025-08-01 17:30:00',
//   amount = 18000.00,
//   company_id = 7,
//   owner_id = 2,
//   vehicle_id = 7
// WHERE id = 7;

// UPDATE contracts SET
//   contract_type = 'Annual',
//   created_at = '2025-08-01 17:30:00',
//   end_date = '2026-08-01',
//   start_date = '2025-08-01',
//   status = 'ACTIVE',
//   updated_at = '2025-08-01 17:30:00',
//   amount = 19000.00,
//   company_id = 8,
//   owner_id = 3,
//   vehicle_id = 8
// WHERE id = 8;

// UPDATE contracts SET
//   contract_type = 'Annual',
//   created_at = '2025-08-01 17:30:00',
//   end_date = '2026-08-01',
//   start_date = '2025-08-01',
//   status = 'ACTIVE',
//   updated_at = '2025-08-01 17:30:00',
//   amount = 20000.00,
//   company_id = 9,
//   owner_id = 4,
//   vehicle_id = 9
// WHERE id = 9;

// UPDATE contracts SET
//   contract_type = 'Annual',
//   created_at = '2025-08-01 17:30:00',
//   end_date = '2026-08-01',
//   start_date = '2025-08-01',
//   status = 'ACTIVE',
//   updated_at = '2025-08-01 17:30:00',
//   amount = 21000.00,
//   company_id = 10,
//   owner_id = 5,
//   vehicle_id = 10
// WHERE id = 10;

package com.ideabotkey.transportrouting.seeders;

import com.ideabotkey.transportrouting.model.*;
import com.ideabotkey.transportrouting.repository.CompanyRepository;
import com.ideabotkey.transportrouting.repository.ContractRepository;
import com.ideabotkey.transportrouting.repository.VehicleOwnerRepository;
import com.ideabotkey.transportrouting.repository.VehicleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ContractSeeder implements CommandLineRunner {

    private final ContractRepository contractRepository;
    private final CompanyRepository companyRepository;
    private final VehicleOwnerRepository vehicleOwnerRepository;
    private final VehicleRepository vehicleRepository;

    public ContractSeeder(
            ContractRepository contractRepository,
            CompanyRepository companyRepository,
            VehicleOwnerRepository vehicleOwnerRepository,
            VehicleRepository vehicleRepository) {
        this.contractRepository = contractRepository;
        this.companyRepository = companyRepository;
        this.vehicleOwnerRepository = vehicleOwnerRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (contractRepository.count() > 0) {
            System.out.println("✅ Contracts already seeded.");
            return;
        }

        List<Company> companies = companyRepository.findAll();
        List<VehicleOwner> owners = vehicleOwnerRepository.findAll();
        List<Vehicle> vehicles = vehicleRepository.findAll();

        if (companies.isEmpty() || owners.isEmpty() || vehicles.isEmpty()) {
            System.out.println("⚠️ Cannot seed Contracts: Missing Companies, Owners, or Vehicles.");
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        List<Contract> contracts = new ArrayList<>();

        // Example: Seed 10 contracts
        for (int i = 0; i < 10; i++) {
            Contract contract = new Contract(
                    companies.get(i % companies.size()),
                    owners.get(i % owners.size()),
                    vehicles.get(i % vehicles.size()),
                    "Annual", // contractType
                    LocalDate.now(),
                    LocalDate.now().plusYears(1),
                    BigDecimal.valueOf(10000 + (i * 2000)),
                    "ACTIVE",
                    now,
                    now);
            contracts.add(contract);
        }

        contractRepository.saveAll(contracts);
        System.out.println("✅ Contracts seeded with " + contracts.size() + " records.");
    }
}
