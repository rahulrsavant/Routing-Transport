package com.ideabotkey.transportrouting.seeders;

import com.ideabotkey.transportrouting.model.Company;
import com.ideabotkey.transportrouting.repository.CompanyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CompanySeeder implements CommandLineRunner {

    private final CompanyRepository companyRepository;

    public CompanySeeder(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (companyRepository.count() == 0) {
            LocalDateTime now = LocalDateTime.now();

            List<Company> companies = List.of(
                    new Company("Telco India Ltd", "Chinchwad, Pune 411019", "Rahul Sawant", "contact.in@Telco.com",
                            "+91-2027407740", "ACTIVE"),
                    new Company("Tata Motors", "Tata Motors Ltd, Pimpri, Pune 411018", "Ajay Kumar",
                            "ajay.kumar@tatamotors.com", "+91-2026605000", "ACTIVE"),
                    new Company("Bajaj Auto Ltd", "Mumbai-Pune Road, Akurdi, Pune 411035", "Snehal Patil",
                            "snehal.patil@bajajauto.co.in", "+91-2027408011", "ACTIVE"),
                    new Company("Hindustan Antibiotics Ltd", "Pimpri, Pune 411018", "Suresh Shah",
                            "suresh.shah@hindantibiotics.com", "+91-2027445555", "ACTIVE"),
                    new Company("Forbes Marshall", "B/85, MIDC Estate, Kasarwadi, Pune 411034", "Priya Joshi",
                            "priya.joshi@forbesmarshall.com", "+91-2027404411", "ACTIVE"),
                    new Company("Thermax Ltd", "D-13, MIDC Industrial Area, Chinchwad, Pune 411019", "Kiran Mehta",
                            "kiran.mehta@thermaxglobal.com", "+91-2027461234", "ACTIVE"),
                    new Company("Emcure Pharmaceuticals", "T-184, MIDC Bhosari, Pune 411026", "Vikas Kulkarni",
                            "vikas.kulkarni@emcure.com", "+91-2027208700", "ACTIVE"),
                    new Company("Sandvik Asia Pvt Ltd", "Mundhwa, Pune 411036", "Meera Desai",
                            "meera.desai@sandvik.com", "+91-2026848200", "ACTIVE"),
                    new Company("Finolex Cables Ltd", "26/27, Mumbai-Pune Road, Pimpri, Pune 411018", "Nitin Bhosale",
                            "nitin.bhosale@finolex.com", "+91-2027427110", "ACTIVE"),
                    new Company("Mahindra CIE Automotive", "Gat No. 604/606, Village Kuruli, Tal Khed, Pune 410501",
                            "Rekha Sharma", "rekha.sharma@cieauto.com", "+91-2026730300", "ACTIVE"));

            companies.forEach(c -> {
                c.setCreatedAt(now);
                c.setUpdatedAt(now);
            });

            companyRepository.saveAll(companies);
            System.out.println("✅ Seeded 10 Pimpri-Chinchwad Companies (incl. Telco)");
        }
    }
}
