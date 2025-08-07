package com.ideabotkey.transportrouting.seeders;

import com.ideabotkey.transportrouting.model.Company;
import com.ideabotkey.transportrouting.model.Contract;
import com.ideabotkey.transportrouting.model.Vehicle;
import com.ideabotkey.transportrouting.model.VehicleOwner;
import com.ideabotkey.transportrouting.repository.CompanyRepository;
import com.ideabotkey.transportrouting.repository.ContractRepository;
import com.ideabotkey.transportrouting.repository.VehicleOwnerRepository;
import com.ideabotkey.transportrouting.repository.VehicleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public void run(String... args) {
        if (contractRepository.count() > 0) {
            System.out.println("✅ Contracts already seeded, skipping.");
            return;
        }

        List<Company> companies = companyRepository.findAll();
        List<VehicleOwner> owners = vehicleOwnerRepository.findAll();
        List<Vehicle> vehicles = vehicleRepository.findAll();

        if (companies.size() < 10 || owners.size() < 5 || vehicles.size() < 10) {
            System.out.println("⚠️ Cannot seed Contracts: Not enough Companies, Owners, or Vehicles.");
            return;
        }

        LocalDate startDate = LocalDate.of(2025, 8, 1);
        LocalDate endDate = LocalDate.of(2026, 8, 1);
        LocalDateTime createdAt = LocalDateTime.of(2025, 8, 1, 17, 30);

        Contract[] contracts = new Contract[] {
                new Contract(companies.get(0), owners.get(0), vehicles.get(0), "Annual", startDate, endDate,
                        new BigDecimal("12000.00"), "ACTIVE", createdAt, createdAt),
                new Contract(companies.get(1), owners.get(1), vehicles.get(1), "Annual", startDate, endDate,
                        new BigDecimal("13000.00"), "ACTIVE", createdAt, createdAt),
                new Contract(companies.get(2), owners.get(2), vehicles.get(2), "Annual", startDate, endDate,
                        new BigDecimal("14000.00"), "ACTIVE", createdAt, createdAt),
                new Contract(companies.get(3), owners.get(3), vehicles.get(3), "Annual", startDate, endDate,
                        new BigDecimal("15000.00"), "ACTIVE", createdAt, createdAt),
                new Contract(companies.get(4), owners.get(4), vehicles.get(4), "Annual", startDate, endDate,
                        new BigDecimal("16000.00"), "ACTIVE", createdAt, createdAt),
                new Contract(companies.get(5), owners.get(0), vehicles.get(5), "Annual", startDate, endDate,
                        new BigDecimal("17000.00"), "ACTIVE", createdAt, createdAt),
                new Contract(companies.get(6), owners.get(1), vehicles.get(6), "Annual", startDate, endDate,
                        new BigDecimal("18000.00"), "ACTIVE", createdAt, createdAt),
                new Contract(companies.get(7), owners.get(2), vehicles.get(7), "Annual", startDate, endDate,
                        new BigDecimal("19000.00"), "ACTIVE", createdAt, createdAt),
                new Contract(companies.get(8), owners.get(3), vehicles.get(8), "Annual", startDate, endDate,
                        new BigDecimal("20000.00"), "ACTIVE", createdAt, createdAt),
                new Contract(companies.get(9), owners.get(4), vehicles.get(9), "Annual", startDate, endDate,
                        new BigDecimal("21000.00"), "ACTIVE", createdAt, createdAt)
        };

        contractRepository.saveAll(List.of(contracts));
        System.out.println("✅ Manually seeded " + contracts.length + " contracts.");
    }
}
