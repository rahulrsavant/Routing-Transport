package com.ideabotkey.transportrouting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ideabotkey.transportrouting.model.Company;
import com.ideabotkey.transportrouting.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(CompanyController.class)
@AutoConfigureMockMvc
class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CompanyRepository repository;

    private Company sampleCompany() {
    	Company company = new Company(
    		    "Acme Logistics",
    		    "123 Main St",
    		    "John Doe",
    		    "john@example.com",
    		    "+1-555-1234",
    		    "ACTIVE"
    		);

        return company;
    }

    @Test
    void getAllCompanies() throws Exception {
        when(repository.findAllWithUsers()).thenReturn(Collections.singletonList(sampleCompany()));

        mockMvc.perform(get("/api/v1/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("TestCo"))
                .andExpect(jsonPath("$[0].address").value("Addr"))
                .andExpect(jsonPath("$[0].users").isArray());
    }

    @Test
    void getCompanyById() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleCompany()));

        mockMvc.perform(get("/api/v1/companies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("TestCo"))
                .andExpect(jsonPath("$.address").value("Addr"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void createCompany() throws Exception {
        when(repository.save(any(Company.class))).thenReturn(sampleCompany());

        mockMvc.perform(post("/api/v1/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleCompany())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("TestCo"))
                .andExpect(jsonPath("$.address").value("Addr"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void updateCompany() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleCompany()));
        when(repository.save(any(Company.class))).thenReturn(sampleCompany());

        mockMvc.perform(put("/api/v1/companies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleCompany())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("TestCo"))
                .andExpect(jsonPath("$.address").value("Addr"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void deleteCompany() throws Exception {
        mockMvc.perform(delete("/api/v1/companies/1"))
                .andExpect(status().isOk());
    }
}
