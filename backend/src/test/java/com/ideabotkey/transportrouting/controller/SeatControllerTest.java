package com.ideabotkey.transportrouting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ideabotkey.transportrouting.model.Seat;
import com.ideabotkey.transportrouting.model.SeatStatus;
import com.ideabotkey.transportrouting.repository.SeatRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(SeatController.class)
@AutoConfigureMockMvc
class SeatControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SeatRepository repository;

    private Seat sampleSeat() {
        Seat s = new Seat();
        s.setId(1L);
        s.setSeatNumber("1A");
        s.setRowNumber(1);
        s.setPrice(100.0);
        s.setStatus(SeatStatus.AVAILABLE);
        s.setCreatedAt(LocalDateTime.now());
        s.setUpdatedAt(LocalDateTime.now());
        return s;
    }

    @Test
    void getSeats() throws Exception {
        when(repository.findAll()).thenReturn(Collections.singletonList(sampleSeat()));

        mockMvc.perform(get("/api/v1/seats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].seatNumber").value("1A"));
    }

    @Test
    void bookSeats() throws Exception {
        when(repository.findAllById(Collections.singletonList(1L)))
                .thenReturn(Collections.singletonList(sampleSeat()));
        when(repository.saveAll(anyList())).thenReturn(Collections.singletonList(sampleSeat()));

        mockMvc.perform(post("/api/v1/seats/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"seatIds\":[1]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}
