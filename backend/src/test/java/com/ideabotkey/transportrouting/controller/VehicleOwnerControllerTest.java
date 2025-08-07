package com.ideabotkey.transportrouting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ideabotkey.transportrouting.model.VehicleOwner;
import com.ideabotkey.transportrouting.repository.VehicleOwnerRepository;
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

@WebMvcTest(VehicleOwnerController.class)
@AutoConfigureMockMvc
class VehicleOwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VehicleOwnerRepository repository;

    private VehicleOwner sampleOwner() {
        VehicleOwner o = new VehicleOwner();
        o.setId(1L);
        o.setName("Owner");
        o.setAddress("Addr");
        o.setCreatedAt(LocalDateTime.now());
        o.setUpdatedAt(LocalDateTime.now());
        return o;
    }

    @Test
    void getAllOwners() throws Exception {
        when(repository.findAll()).thenReturn(Collections.singletonList(sampleOwner()));

        mockMvc.perform(get("/api/v1/owners"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Owner"))
                .andExpect(jsonPath("$[0].address").value("Addr"))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].createdAt").exists())
                .andExpect(jsonPath("$[0].updatedAt").exists());
    }

    @Test
    void getOwnerById() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleOwner()));

        mockMvc.perform(get("/api/v1/owners/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Owner"))
                .andExpect(jsonPath("$.address").value("Addr"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void createOwner() throws Exception {
        when(repository.save(any(VehicleOwner.class))).thenReturn(sampleOwner());

        mockMvc.perform(post("/api/v1/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleOwner())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Owner"))
                .andExpect(jsonPath("$.address").value("Addr"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void updateOwner() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleOwner()));
        when(repository.save(any(VehicleOwner.class))).thenReturn(sampleOwner());

        mockMvc.perform(put("/api/v1/owners/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleOwner())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Owner"))
                .andExpect(jsonPath("$.address").value("Addr"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void deleteOwner() throws Exception {
        mockMvc.perform(delete("/api/v1/owners/1"))
                .andExpect(status().isOk());
    }
}
