package com.ideabotkey.transportrouting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ideabotkey.transportrouting.model.Employee;
import com.ideabotkey.transportrouting.repository.EmployeeRepository;
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

@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeRepository repository;

    private Employee sampleEmployee() {
        Employee e = new Employee();
        e.setId(1L);
        e.setName("Emp");
        e.setEmail("e@test.com");
        e.setCreatedAt(LocalDateTime.now());
        e.setUpdatedAt(LocalDateTime.now());
        return e;
    }

    @Test
    void getAllEmployees() throws Exception {
        when(repository.findAll()).thenReturn(Collections.singletonList(sampleEmployee()));

        mockMvc.perform(get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Emp"))
                .andExpect(jsonPath("$[0].email").value("e@test.com"))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].createdAt").exists())
                .andExpect(jsonPath("$[0].updatedAt").exists());
    }

    @Test
    void getEmployeeById() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleEmployee()));

        mockMvc.perform(get("/api/v1/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Emp"))
                .andExpect(jsonPath("$.email").value("e@test.com"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void createEmployee() throws Exception {
        when(repository.save(any(Employee.class))).thenReturn(sampleEmployee());

        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleEmployee())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Emp"))
                .andExpect(jsonPath("$.email").value("e@test.com"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void updateEmployee() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleEmployee()));
        when(repository.save(any(Employee.class))).thenReturn(sampleEmployee());

        mockMvc.perform(put("/api/v1/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleEmployee())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Emp"))
                .andExpect(jsonPath("$.email").value("e@test.com"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void deleteEmployee() throws Exception {
        mockMvc.perform(delete("/api/v1/employees/1"))
                .andExpect(status().isOk());
    }
}
