package com.ideabotkey.transportrouting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ideabotkey.transportrouting.model.Vehicle;
import com.ideabotkey.transportrouting.repository.VehicleRepository;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(VehicleController.class)
@AutoConfigureMockMvc
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VehicleRepository repository;

    private Vehicle sampleVehicle() {
        Vehicle v = new Vehicle();
        v.setId(1L);
        v.setVehicleType("Bus");
        v.setRegistrationNumber("ABC123");
        v.setCreatedAt(LocalDateTime.now());
        v.setUpdatedAt(LocalDateTime.now());
        return v;
    }

    @Test
    void getAllVehicles() throws Exception {
        when(repository.findAll()).thenReturn(Collections.singletonList(sampleVehicle()));

        mockMvc.perform(get("/api/v1/vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].vehicleType").value("Bus"))
                .andExpect(jsonPath("$[0].registrationNumber").value("ABC123"))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].createdAt").exists())
                .andExpect(jsonPath("$[0].updatedAt").exists());
    }

    @Test
    void getVehicleById() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleVehicle()));

        mockMvc.perform(get("/api/v1/vehicles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleType").value("Bus"))
                .andExpect(jsonPath("$.registrationNumber").value("ABC123"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void createVehicle() throws Exception {
        when(repository.save(any(Vehicle.class))).thenReturn(sampleVehicle());

        mockMvc.perform(post("/api/v1/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleVehicle())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleType").value("Bus"))
                .andExpect(jsonPath("$.registrationNumber").value("ABC123"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void updateVehicle() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleVehicle()));
        when(repository.save(any(Vehicle.class))).thenReturn(sampleVehicle());

        mockMvc.perform(put("/api/v1/vehicles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleVehicle())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleType").value("Bus"))
                .andExpect(jsonPath("$.registrationNumber").value("ABC123"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void deleteVehicle() throws Exception {
        mockMvc.perform(delete("/api/v1/vehicles/1"))
                .andExpect(status().isOk());
    }
}
