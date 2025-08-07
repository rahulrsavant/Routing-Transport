package com.ideabotkey.transportrouting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ideabotkey.transportrouting.model.TripEmployee;
import com.ideabotkey.transportrouting.repository.TripEmployeeRepository;
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

@WebMvcTest(TripEmployeeController.class)
@AutoConfigureMockMvc
class TripEmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TripEmployeeRepository repository;

    private TripEmployee sampleTripEmployee() {
        TripEmployee te = new TripEmployee();
        te.setId(1L);
        te.setPickUpPoint("A");
        te.setDropOffPoint("B");
        te.setCreatedAt(LocalDateTime.now());
        te.setUpdatedAt(LocalDateTime.now());
        return te;
    }

    @Test
    void getAllTripEmployees() throws Exception {
        when(repository.findAll()).thenReturn(Collections.singletonList(sampleTripEmployee()));

        mockMvc.perform(get("/api/v1/trip-employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].pickUpPoint").value("A"))
                .andExpect(jsonPath("$[0].dropOffPoint").value("B"))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].createdAt").exists())
                .andExpect(jsonPath("$[0].updatedAt").exists());
    }

    @Test
    void getTripEmployeeById() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleTripEmployee()));

        mockMvc.perform(get("/api/v1/trip-employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pickUpPoint").value("A"))
                .andExpect(jsonPath("$.dropOffPoint").value("B"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void createTripEmployee() throws Exception {
        when(repository.save(any(TripEmployee.class))).thenReturn(sampleTripEmployee());

        mockMvc.perform(post("/api/v1/trip-employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleTripEmployee())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pickUpPoint").value("A"))
                .andExpect(jsonPath("$.dropOffPoint").value("B"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void updateTripEmployee() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleTripEmployee()));
        when(repository.save(any(TripEmployee.class))).thenReturn(sampleTripEmployee());

        mockMvc.perform(put("/api/v1/trip-employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleTripEmployee())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pickUpPoint").value("A"))
                .andExpect(jsonPath("$.dropOffPoint").value("B"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void deleteTripEmployee() throws Exception {
        mockMvc.perform(delete("/api/v1/trip-employees/1"))
                .andExpect(status().isOk());
    }
}
