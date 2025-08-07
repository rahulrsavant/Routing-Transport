package com.ideabotkey.transportrouting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class LocationResponse {
    private String source; // "driver" or "employee"
    private double latitude;
    private double longitude;
    private Instant timestamp;
    private Long employeeId;
}
