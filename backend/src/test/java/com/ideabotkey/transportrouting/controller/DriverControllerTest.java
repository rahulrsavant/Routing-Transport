//package com.ideabotkey.transportrouting.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ideabotkey.transportrouting.model.Driver;
//import com.ideabotkey.transportrouting.model.Vehicle;
//import com.ideabotkey.transportrouting.model.VehicleOwner;
//import com.ideabotkey.transportrouting.repository.DriverRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.hamcrest.Matchers.*;
//
//@WebMvcTest(DriverController.class)
//@AutoConfigureMockMvc
//class DriverControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private DriverRepository repository;
//
//    private Driver sampleDriver() {
//        // 1️⃣ Create a VehicleOwner
//        VehicleOwner owner = new VehicleOwner(
//            "John Owner",
//            "john@example.com",
//            "+1-555-1234",
//            "123 Owner St, Springfield",
//            "ACTIVE"
//        );
//        owner.setCreatedAt(LocalDateTime.now());
//        owner.setUpdatedAt(LocalDateTime.now());
//
//        // 2️⃣ Create a Vehicle assigned to the owner
//        Vehicle vehicle = new Vehicle(
//            owner,
//            "Bus",
//            "ABC-123",
//            40,
//            "Model X",
//            "ACTIVE"
//        );
//        vehicle.setCreatedAt(LocalDateTime.now());
//        vehicle.setUpdatedAt(LocalDateTime.now());
//
//        // 3️⃣ Create a Driver assigned to the vehicle
//        Driver driver = new Driver(
//            "Driver Name",
//            "+1-555-5678",
//            "DL-001",
//            "456 Driver Rd, Springfield",
//            vehicle,
//            "ACTIVE"
//        );
//        driver.setCreatedAt(LocalDateTime.now());
//        driver.setUpdatedAt(LocalDateTime.now());
//
//        return driver;
//    }
//
//
//
//    @Test
//    void getAllDrivers() throws Exception {
//        when(repository.findAll()).thenReturn(Collections.singletonList(sampleDriver()));
//
//        mockMvc.perform(get("/api/v1/drivers"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].name").value("Driver"))
//                .andExpect(jsonPath("$[0].phone").value("123"))
//                .andExpect(jsonPath("$[0].id").exists())
//                .andExpect(jsonPath("$[0].createdAt").exists())
//                .andExpect(jsonPath("$[0].updatedAt").exists());
//    }
//
//    @Test
//    void getDriverById() throws Exception {
//        when(repository.findById(1L)).thenReturn(Optional.of(sampleDriver()));
//
//        mockMvc.perform(get("/api/v1/drivers/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Driver"))
//                .andExpect(jsonPath("$.phone").value("123"))
//                .andExpect(jsonPath("$.id").exists())
//                .andExpect(jsonPath("$.createdAt").exists())
//                .andExpect(jsonPath("$.updatedAt").exists());
//    }
//
//    @Test
//    void createDriver() throws Exception {
//        when(repository.save(any(Driver.class))).thenReturn(sampleDriver());
//
//        mockMvc.perform(post("/api/v1/drivers")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(sampleDriver())))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Driver"))
//                .andExpect(jsonPath("$.phone").value("123"))
//                .andExpect(jsonPath("$.id").exists())
//                .andExpect(jsonPath("$.createdAt").exists())
//                .andExpect(jsonPath("$.updatedAt").exists());
//    }
//
//    @Test
//    void updateDriver() throws Exception {
//        when(repository.findById(1L)).thenReturn(Optional.of(sampleDriver()));
//        when(repository.save(any(Driver.class))).thenReturn(sampleDriver());
//
//        mockMvc.perform(put("/api/v1/drivers/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(sampleDriver())))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Driver"))
//                .andExpect(jsonPath("$.phone").value("123"))
//                .andExpect(jsonPath("$.id").exists())
//                .andExpect(jsonPath("$.createdAt").exists())
//                .andExpect(jsonPath("$.updatedAt").exists());
//    }
//
//    @Test
//    void deleteDriver() throws Exception {
//        mockMvc.perform(delete("/api/v1/drivers/1"))
//                .andExpect(status().isOk());
//    }
//}
