package com.ideabotkey.transportrouting.seeders;

import com.ideabotkey.transportrouting.model.Contract;
import com.ideabotkey.transportrouting.model.Route;
import com.ideabotkey.transportrouting.repository.ContractRepository;
import com.ideabotkey.transportrouting.repository.RouteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class RouteSeeder implements CommandLineRunner {

        private final RouteRepository routeRepository;
        private final ContractRepository contractRepository;

        public RouteSeeder(RouteRepository routeRepository, ContractRepository contractRepository) {
                this.routeRepository = routeRepository;
                this.contractRepository = contractRepository;
        }

        @Override
        public void run(String... args) throws Exception {
                if (routeRepository.count() == 0) {
                        List<Contract> contracts = contractRepository.findAll();
                        if (contracts.isEmpty()) {
                                System.out.println("⚠️ Cannot seed Routes: no Contracts found.");
                                return;
                        }

                        LocalDateTime now = LocalDateTime.now();
                        List<Route> routes = new ArrayList<>();

                        // ---- Telco to Destination ----
                        routes.add(new Route("Telco to Pune Station", "Telco", "Pune Station",
                                        Arrays.asList("Telco", "Nashik Phata", "Khadki", "Sancheti", "Pune Station"),
                                        "45m", contracts.get(0 % contracts.size()), "ACTIVE"));
                        routes.add(new Route("Telco to Swargate", "Telco", "Swargate",
                                        Arrays.asList("Telco", "Nashik Phata", "Khadki", "Sancheti", "Dagdusheth",
                                                        "Swargate"),
                                        "55m", contracts.get(1 % contracts.size()), "ACTIVE"));
                        routes.add(new Route("Telco to Katraj", "Telco", "Katraj",
                                        Arrays.asList("Telco", "Nashik Phata", "Khadki", "Sancheti", "Dagdusheth",
                                                        "Swargate", "Katraj"),
                                        "1h 10m", contracts.get(2 % contracts.size()), "ACTIVE"));
                        routes.add(new Route("Telco to Dange Chowk", "Telco", "Dange Chowk",
                                        Arrays.asList("Telco", "Thergaon", "Dange Chowk"),
                                        "35m", contracts.get(3 % contracts.size()), "ACTIVE"));
                        routes.add(new Route("Telco to Hinjawadi", "Telco", "Hinjawadi IT Park",
                                        Arrays.asList("Telco", "Kalewadi", "Wakad", "Hinjawadi IT Park"),
                                        "50m", contracts.get(4 % contracts.size()), "ACTIVE"));

                        // ---- Destination to Telco (reverse) ----
                        routes.add(new Route("Pune Station to Telco", "Pune Station", "Telco",
                                        Arrays.asList("Pune Station", "Sancheti", "Khadki", "Nashik Phata", "Telco"),
                                        "45m", contracts.get(0 % contracts.size()), "ACTIVE"));
                        routes.add(new Route("Swargate to Telco", "Swargate", "Telco",
                                        Arrays.asList("Swargate", "Dagdusheth", "Sancheti", "Khadki", "Nashik Phata",
                                                        "Telco"),
                                        "55m", contracts.get(1 % contracts.size()), "ACTIVE"));
                        routes.add(new Route("Katraj to Telco", "Katraj", "Telco",
                                        Arrays.asList("Katraj", "Swargate", "Dagdusheth", "Sancheti", "Khadki",
                                                        "Nashik Phata", "Telco"),
                                        "1h 10m", contracts.get(2 % contracts.size()), "ACTIVE"));
                        routes.add(new Route("Dange Chowk to Telco", "Dange Chowk", "Telco",
                                        Arrays.asList("Dange Chowk", "Thergaon", "Telco"),
                                        "35m", contracts.get(3 % contracts.size()), "ACTIVE"));
                        routes.add(new Route("Hinjawadi to Telco", "Hinjawadi IT Park", "Telco",
                                        Arrays.asList("Hinjawadi IT Park", "Wakad", "Kalewadi", "Telco"),
                                        "50m", contracts.get(4 % contracts.size()), "ACTIVE"));

                        // ---- Telco to Destination (more) ----
                        routes.add(new Route("Telco to Bhosari", "Telco", "Bhosari",
                                        Arrays.asList("Telco", "Nashik Phata", "Bhosari"), "25m",
                                        contracts.get(5 % contracts.size()), "ACTIVE"));
                        routes.add(new Route("Telco to Akurdi", "Telco", "Akurdi",
                                        Arrays.asList("Telco", "Nashik Phata", "Akurdi"), "30m",
                                        contracts.get(6 % contracts.size()), "ACTIVE"));
                        routes.add(new Route("Telco to Pimpri", "Telco", "Pimpri",
                                        Arrays.asList("Telco", "Nashik Phata", "Pimpri"), "20m",
                                        contracts.get(7 % contracts.size()), "ACTIVE"));
                        routes.add(new Route("Telco to Talegaon", "Telco", "Talegaon",
                                        Arrays.asList("Telco", "Nashik Phata", "Akurdi", "Dehu Road", "Talegaon"), "1h",
                                        contracts.get(8 % contracts.size()), "ACTIVE"));
                        routes.add(new Route("Telco to Pimple Saudagar", "Telco", "Pimple Saudagar",
                                        Arrays.asList("Telco", "Kalewadi", "Pimple Saudagar"), "25m",
                                        contracts.get(9 % contracts.size()), "ACTIVE"));

                        // ---- Destination to Telco (more, reverse) ----
                        routes.add(new Route("Bhosari to Telco", "Bhosari", "Telco",
                                        Arrays.asList("Bhosari", "Nashik Phata", "Telco"), "25m",
                                        contracts.get(5 % contracts.size()), "ACTIVE"));
                        routes.add(new Route("Akurdi to Telco", "Akurdi", "Telco",
                                        Arrays.asList("Akurdi", "Nashik Phata", "Telco"), "30m",
                                        contracts.get(6 % contracts.size()), "ACTIVE"));
                        routes.add(new Route("Pimpri to Telco", "Pimpri", "Telco",
                                        Arrays.asList("Pimpri", "Nashik Phata", "Telco"), "20m",
                                        contracts.get(7 % contracts.size()), "ACTIVE"));
                        routes.add(new Route("Talegaon to Telco", "Talegaon", "Telco",
                                        Arrays.asList("Talegaon", "Dehu Road", "Akurdi", "Nashik Phata", "Telco"), "1h",
                                        contracts.get(8 % contracts.size()), "ACTIVE"));
                        routes.add(new Route("Pimple Saudagar to Telco", "Pimple Saudagar", "Telco",
                                        Arrays.asList("Pimple Saudagar", "Kalewadi", "Telco"), "25m",
                                        contracts.get(9 % contracts.size()), "ACTIVE"));

                        routes.forEach(r -> {
                                r.setCreatedAt(now);
                                r.setUpdatedAt(now);
                        });

                        routeRepository.saveAll(routes);
                        System.out.println("✅ Seeded 10 Telco Routes (5 up, 5 down)");
                }
        }
}
