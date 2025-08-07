package com.ideabotkey.transportrouting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ideabotkey.transportrouting.model.Route;
import com.ideabotkey.transportrouting.repository.RouteRepository;
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

@WebMvcTest(RouteController.class)
@AutoConfigureMockMvc
class RouteControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RouteRepository repository;

    private Route sampleRoute() {
        Route r = new Route();
        r.setId(1L);
        r.setName("Route");
        r.setStartPoint("A");
        r.setEndPoint("B");
        r.setCreatedAt(LocalDateTime.now());
        r.setUpdatedAt(LocalDateTime.now());
        return r;
    }

    @Test
    void getAllRoutes() throws Exception {
        when(repository.findAll()).thenReturn(Collections.singletonList(sampleRoute()));

        mockMvc.perform(get("/api/v1/routes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Route"))
                .andExpect(jsonPath("$[0].startPoint").value("A"))
                .andExpect(jsonPath("$[0].endPoint").value("B"))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].createdAt").exists())
                .andExpect(jsonPath("$[0].updatedAt").exists());
    }

    @Test
    void getRouteById() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleRoute()));

        mockMvc.perform(get("/api/v1/routes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Route"))
                .andExpect(jsonPath("$.startPoint").value("A"))
                .andExpect(jsonPath("$.endPoint").value("B"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void createRoute() throws Exception {
        when(repository.save(any(Route.class))).thenReturn(sampleRoute());

        mockMvc.perform(post("/api/v1/routes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleRoute())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Route"))
                .andExpect(jsonPath("$.startPoint").value("A"))
                .andExpect(jsonPath("$.endPoint").value("B"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void updateRoute() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleRoute()));
        when(repository.save(any(Route.class))).thenReturn(sampleRoute());

        mockMvc.perform(put("/api/v1/routes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleRoute())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Route"))
                .andExpect(jsonPath("$.startPoint").value("A"))
                .andExpect(jsonPath("$.endPoint").value("B"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void deleteRoute() throws Exception {
        mockMvc.perform(delete("/api/v1/routes/1"))
                .andExpect(status().isOk());
    }
}
