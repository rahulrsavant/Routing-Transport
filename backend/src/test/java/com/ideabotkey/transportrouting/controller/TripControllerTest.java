package com.ideabotkey.transportrouting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ideabotkey.transportrouting.model.Trip;
import com.ideabotkey.transportrouting.repository.TripRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(TripController.class)
@AutoConfigureMockMvc
class TripControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TripRepository repository;

    private Trip sampleTrip() {
        Trip t = new Trip();
        t.setId(1L);
        t.setDate(LocalDate.now());
        t.setStartTime("08:00");
        t.setEndTime("09:00");
        t.setCreatedAt(LocalDateTime.now());
        t.setUpdatedAt(LocalDateTime.now());
        return t;
    }

    @Test
    void getAllTrips() throws Exception {
        when(repository.findAll()).thenReturn(Collections.singletonList(sampleTrip()));

        mockMvc.perform(get("/api/v1/trips"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].startTime").value("08:00"))
                .andExpect(jsonPath("$[0].endTime").value("09:00"))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].date").exists())
                .andExpect(jsonPath("$[0].createdAt").exists())
                .andExpect(jsonPath("$[0].updatedAt").exists());
    }

    @Test
    void getTripById() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleTrip()));

        mockMvc.perform(get("/api/v1/trips/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.startTime").value("08:00"))
                .andExpect(jsonPath("$.endTime").value("09:00"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.date").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void createTrip() throws Exception {
        when(repository.save(any(Trip.class))).thenReturn(sampleTrip());

        mockMvc.perform(post("/api/v1/trips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleTrip())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.startTime").value("08:00"))
                .andExpect(jsonPath("$.endTime").value("09:00"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.date").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void updateTrip() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleTrip()));
        when(repository.save(any(Trip.class))).thenReturn(sampleTrip());

        mockMvc.perform(put("/api/v1/trips/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleTrip())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.startTime").value("08:00"))
                .andExpect(jsonPath("$.endTime").value("09:00"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.date").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void deleteTrip() throws Exception {
        mockMvc.perform(delete("/api/v1/trips/1"))
                .andExpect(status().isOk());
    }
}
