package com.ideabotkey.transportrouting.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class LocationUpdateRequest {
    private Long tripId;
    private Long employeeId; // null if driver
    private boolean isDriver;
    private double latitude;
    private double longitude;
    private Instant timestamp;
}
