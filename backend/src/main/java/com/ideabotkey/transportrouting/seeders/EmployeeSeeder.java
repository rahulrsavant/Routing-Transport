package com.ideabotkey.transportrouting.seeders;

import com.ideabotkey.transportrouting.model.Company;
import com.ideabotkey.transportrouting.model.Employee;
import com.ideabotkey.transportrouting.repository.CompanyRepository;
import com.ideabotkey.transportrouting.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class EmployeeSeeder implements CommandLineRunner {

        private final EmployeeRepository employeeRepository;
        private final CompanyRepository companyRepository;

        public EmployeeSeeder(EmployeeRepository employeeRepository, CompanyRepository companyRepository) {
                this.employeeRepository = employeeRepository;
                this.companyRepository = companyRepository;
        }

        @Override
        public void run(String... args) throws Exception {
                if (employeeRepository.count() > 0) {
                        System.out.println("✅ Employees already exist, skipping EmployeeSeeder.");
                        return;
                }

                List<Company> companies = companyRepository.findAll();
                if (companies == null || companies.size() < 10) {
                        System.out.println("⚠️ Cannot seed Employees: insufficient Companies found.");
                        return;
                }
                LocalDateTime now = LocalDateTime.now();

                Employee e1 = new Employee(null, "Rahul Sawant", "rahul.sawant@example.com", "+91-7798787874",
                                "123 MG Road, Bengaluru", "ACTIVE", now, now, companies.get(0));
                Employee e2 = new Employee(null, "Bharat Shinde", "bharat.shinde@example.com", "+91-9049583898",
                                "456 Park Street, Mumbai", "ACTIVE", now, now, companies.get(1));
                Employee e3 = new Employee(null, "Raj Madhav Verma", "raj.verma@example.com", "+91-9123123123",
                                "321 Connaught Place, Delhi", "ACTIVE", now, now, companies.get(4));
                Employee e4 = new Employee(null, "Neha Sitaram Joshi", "neha.joshi@example.com", "+91-9876789876",
                                "987 FC Road, Pune", "ACTIVE", now, now, companies.get(5));
                Employee e5 = new Employee(null, "Kavita Narayan Nair", "kavita.nair@example.com", "+91-9876001234",
                                "555 Banjara Hills, Hyderabad", "ACTIVE", now, now, companies.get(7));
                Employee e6 = new Employee(null, "Pooja Ramchandra Mehra", "pooja.mehra@example.com", "+91-9008007006",
                                "777 Sector 17, Chandigarh", "ACTIVE", now, now, companies.get(9));

                employeeRepository.saveAll(List.of(e1, e2, e3, e4, e5, e6));
                System.out.println("✅ 6 Employees seeded successfully.");
        }
}
