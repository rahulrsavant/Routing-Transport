package com.ideabotkey.transportrouting.seeders;

import com.ideabotkey.transportrouting.model.*;
import com.ideabotkey.transportrouting.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;

@Component
public class UserSeeder implements CommandLineRunner {

        private final UserRepository userRepository;
        private final CompanyRepository companyRepository;
        private final RoleRepository roleRepository;
        private final EmployeeRepository employeeRepository;
        private final DriverRepository driverRepository;
        private final VehicleOwnerRepository vehicleOwnerRepository;

        public UserSeeder(
                        UserRepository userRepository,
                        CompanyRepository companyRepository,
                        RoleRepository roleRepository,
                        EmployeeRepository employeeRepository,
                        DriverRepository driverRepository,
                        VehicleOwnerRepository vehicleOwnerRepository) {
                this.userRepository = userRepository;
                this.companyRepository = companyRepository;
                this.roleRepository = roleRepository;
                this.employeeRepository = employeeRepository;
                this.driverRepository = driverRepository;
                this.vehicleOwnerRepository = vehicleOwnerRepository;
        }

        @Override
        public void run(String... args) throws Exception {
                if (userRepository.count() == 0) {
                        LocalDateTime now = LocalDateTime.now();

                        List<Company> companies = companyRepository.findAll();
                        List<Role> roles = roleRepository.findAll();
                        List<Employee> employees = employeeRepository.findAll();
                        List<Driver> drivers = driverRepository.findAll();
                        List<VehicleOwner> owners = vehicleOwnerRepository.findAll();

                        User u1 = new User();
                        u1.setUsername("rahul");
                        u1.setEmail("rahul.sawant@example.com");
                        u1.setMobile("+91-9876543210");
                        u1.setPassword("123456");
                        u1.setRole("ADMIN");
                        u1.setStatus("ACTIVE");
                        u1.setCreatedAt(now);
                        u1.setUpdatedAt(now);
                        u1.setCompanies(new HashSet<>(List.of(companies.get(0))));
                        u1.setRoles(new HashSet<>(List.of(roles.get(0))));
                        u1.setPermissions(EnumSet.allOf(Permission.class));
                        // Linked as employee
                        u1.setEmployee(employees.get(0));
                        u1.setDriver(null);
                        u1.setOwner(null);

                        User u2 = new User();
                        u2.setUsername("bharat");
                        u2.setEmail("bharat.shinde@example.com");
                        u2.setMobile("+91-9049583898");
                        u2.setPassword("123456");
                        u2.setRole("ADMIN");
                        u2.setStatus("ACTIVE");
                        u2.setCreatedAt(now);
                        u2.setUpdatedAt(now);
                        u2.setCompanies(new HashSet<>(List.of(companies.get(1))));
                        u2.setRoles(new HashSet<>(List.of(roles.get(1))));
                        u2.setPermissions(EnumSet.of(Permission.VIEW_USER, Permission.CREATE_USER,
                                        Permission.UPDATE_USER, Permission.DELETE_USER, Permission.VIEW_ROLE));
                        // Linked as employee
                        u2.setEmployee(employees.get(1));
                        u2.setDriver(null);
                        u2.setOwner(null);

                        User u3 = new User();
                        u3.setUsername("amit");
                        u3.setEmail("amit@example.com");
                        u3.setMobile("+91-9988776655");
                        u3.setPassword("123456");
                        u3.setRole("DRIVER");
                        u3.setStatus("ACTIVE");
                        u3.setCreatedAt(now);
                        u3.setUpdatedAt(now);
                        u3.setCompanies(new HashSet<>(List.of(companies.get(2))));
                        u3.setRoles(new HashSet<>(List.of(roles.get(2))));
                        u3.setPermissions(EnumSet.of(
                                        Permission.VIEW_DRIVER, Permission.CREATE_DRIVER,
                                        Permission.UPDATE_DRIVER, Permission.DELETE_DRIVER,
                                        Permission.VIEW_ROUTE));
                        // Linked as driver
                        u3.setDriver(drivers.get(0));
                        u3.setEmployee(null);
                        u3.setOwner(null);

                        User u4 = new User();
                        u4.setUsername("sneha");
                        u4.setEmail("sneha@example.com");
                        u4.setMobile("+91-9876123456");
                        u4.setPassword("123456");
                        u4.setRole("OWNER");
                        u4.setStatus("ACTIVE");
                        u4.setCreatedAt(now);
                        u4.setUpdatedAt(now);
                        u4.setCompanies(new HashSet<>(List.of(companies.get(3))));
                        u4.setRoles(new HashSet<>(List.of(roles.get(3))));
                        u4.setPermissions(EnumSet.of(
                                        Permission.VIEW_OWNER, Permission.CREATE_OWNER,
                                        Permission.UPDATE_OWNER, Permission.DELETE_OWNER,
                                        Permission.VIEW_PERMISSION));
                        // Linked as owner
                        u4.setOwner(owners.get(0));
                        u4.setEmployee(null);
                        u4.setDriver(null);

                        User u5 = new User();
                        u5.setUsername("raj");
                        u5.setEmail("raj@example.com");
                        u5.setMobile("+91-9123123123");
                        u5.setPassword("123456");
                        u5.setRole("MANAGER");
                        u5.setStatus("ACTIVE");
                        u5.setCreatedAt(now);
                        u5.setUpdatedAt(now);
                        u5.setCompanies(new HashSet<>(List.of(companies.get(4))));
                        u5.setRoles(new HashSet<>(List.of(roles.get(1))));
                        u5.setPermissions(EnumSet.of(
                                        Permission.VIEW_USER, Permission.CREATE_USER,
                                        Permission.UPDATE_USER, Permission.DELETE_USER,
                                        Permission.VIEW_COMPANY, Permission.CREATE_COMPANY,
                                        Permission.UPDATE_COMPANY));
                        // Linked as employee
                        u5.setEmployee(employees.get(2));
                        u5.setDriver(null);
                        u5.setOwner(null);

                        User u6 = new User();
                        u6.setUsername("neha");
                        u6.setEmail("neha@example.com");
                        u6.setMobile("+91-9876789876");
                        u6.setPassword("123456");
                        u6.setRole("USER");
                        u6.setStatus("ACTIVE");
                        u6.setCreatedAt(now);
                        u6.setUpdatedAt(now);
                        u6.setCompanies(new HashSet<>(List.of(companies.get(5))));
                        u6.setRoles(new HashSet<>(List.of(roles.get(2))));
                        u6.setPermissions(EnumSet.of(
                                        Permission.VIEW_COMPANY, Permission.CREATE_COMPANY,
                                        Permission.UPDATE_COMPANY, Permission.DELETE_COMPANY));
                        // Linked as employee
                        u6.setEmployee(employees.get(3));
                        u6.setDriver(null);
                        u6.setOwner(null);

                        User u7 = new User();
                        u7.setUsername("ankit");
                        u7.setEmail("ankit@example.com");
                        u7.setMobile("+91-9998887776");
                        u7.setPassword("123456");
                        u7.setRole("DRIVER");
                        u7.setStatus("ACTIVE");
                        u7.setCreatedAt(now);
                        u7.setUpdatedAt(now);
                        u7.setCompanies(new HashSet<>(List.of(companies.get(6))));
                        u7.setRoles(new HashSet<>(List.of(roles.get(2))));
                        u7.setPermissions(EnumSet.of(
                                        Permission.VIEW_COMPANY, Permission.CREATE_COMPANY,
                                        Permission.UPDATE_COMPANY, Permission.DELETE_COMPANY,
                                        Permission.VIEW_CONTRACT, Permission.CREATE_CONTRACT));
                        // Linked as driver
                        u7.setDriver(drivers.get(1));
                        u7.setEmployee(null);
                        u7.setOwner(null);

                        User u8 = new User();
                        u8.setUsername("kavita");
                        u8.setEmail("kavita@example.com");
                        u8.setMobile("+91-9876001234");
                        u8.setPassword("123456");
                        u8.setRole("USER");
                        u8.setStatus("ACTIVE");
                        u8.setCreatedAt(now);
                        u8.setUpdatedAt(now);
                        u8.setCompanies(new HashSet<>(List.of(companies.get(7))));
                        u8.setRoles(new HashSet<>(List.of(roles.get(1))));
                        u8.setPermissions(EnumSet.of(
                                        Permission.VIEW_COMPANY, Permission.CREATE_COMPANY,
                                        Permission.UPDATE_COMPANY));
                        // Linked as employee
                        u8.setEmployee(employees.get(4));
                        u8.setDriver(null);
                        u8.setOwner(null);

                        User u9 = new User();
                        u9.setUsername("vikas");
                        u9.setEmail("vikas@example.com");
                        u9.setMobile("+91-9988776655");
                        u9.setPassword("123456");
                        u9.setRole("OWNER");
                        u9.setStatus("ACTIVE");
                        u9.setCreatedAt(now);
                        u9.setUpdatedAt(now);
                        u9.setCompanies(new HashSet<>(List.of(companies.get(8))));
                        u9.setRoles(new HashSet<>(List.of(roles.get(3))));
                        u9.setPermissions(EnumSet.of(
                                        Permission.VIEW_COMPANY, Permission.CREATE_COMPANY,
                                        Permission.UPDATE_COMPANY, Permission.DELETE_COMPANY,
                                        Permission.VIEW_CONTRACT, Permission.CREATE_CONTRACT,
                                        Permission.UPDATE_CONTRACT, Permission.DELETE_CONTRACT));
                        // Linked as owner
                        u9.setOwner(owners.get(1));
                        u9.setEmployee(null);
                        u9.setDriver(null);

                        User u10 = new User();
                        u10.setUsername("pooja");
                        u10.setEmail("pooja@example.com");
                        u10.setMobile("+91-9008007006");
                        u10.setPassword("123456");
                        u10.setRole("ADMIN");
                        u10.setStatus("ACTIVE");
                        u10.setCreatedAt(now);
                        u10.setUpdatedAt(now);
                        u10.setCompanies(new HashSet<>(List.of(companies.get(9))));
                        u10.setRoles(new HashSet<>(List.of(roles.get(0))));
                        u10.setPermissions(EnumSet.of(
                                        Permission.VIEW_COMPANY, Permission.CREATE_COMPANY,
                                        Permission.UPDATE_COMPANY, Permission.DELETE_COMPANY,
                                        Permission.VIEW_CONTRACT));
                        // Linked as employee
                        u10.setEmployee(employees.get(5));
                        u10.setDriver(null);
                        u10.setOwner(null);

                        userRepository.saveAll(List.of(u1, u2, u3, u4, u5, u6, u7, u8, u9, u10));
                        System.out.println("✅ Seeded 10 Users");
                }
        }
}
