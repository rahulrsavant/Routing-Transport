package com.ideabotkey.transportrouting.controller;

import com.ideabotkey.transportrouting.model.Driver;
import com.ideabotkey.transportrouting.repository.DriverRepository;
import com.ideabotkey.transportrouting.repository.TripRepository;
import com.ideabotkey.transportrouting.repository.UserRepository;
import com.ideabotkey.transportrouting.service.GtfsService;
import com.ideabotkey.transportrouting.service.PermissionChecker;
import org.springframework.http.ResponseEntity;

import com.ideabotkey.transportrouting.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
// import statements...
import java.util.HashMap;
import com.google.transit.realtime.GtfsRealtime.VehiclePosition;
// ... other imports

@RestController
@RequestMapping("/api/vehicles")
public class GTFSVehicleController {

    private final GtfsService gtfsService;

    public GTFSVehicleController(GtfsService gtfsService) {
        this.gtfsService = gtfsService;
    }

    @GetMapping
    public List<Map<String, Object>> getVehicles() throws IOException {
        List<VehiclePosition> vehicles = gtfsService.fetchVehicles();
        return vehicles.stream().map(v -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", v.getVehicle().getId());
            map.put("lat", v.getPosition().getLatitude());
            map.put("lng", v.getPosition().getLongitude());
            map.put("bearing", v.getPosition().getBearing());
            return map;
        }).collect(Collectors.toList());
    }
}
