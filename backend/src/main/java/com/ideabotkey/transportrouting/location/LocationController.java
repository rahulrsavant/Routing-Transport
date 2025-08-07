package com.ideabotkey.transportrouting.location;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class LocationController {

    private final LocationRepository repository;

    public LocationController(LocationRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/location")
    public ResponseEntity<Map<String, String>> save(@RequestBody Location location,
            @RequestHeader("X-User-Id") Long userId) {
        repository.save(location);
        return ResponseEntity.ok(Map.of("status", "saved"));
    }

    @GetMapping("/locations")
    public List<Location> latest(@RequestHeader("X-User-Id") Long userId) {
        List<Location> vehicles = repository.findByType("vehicle");
        List<Location> commuters = repository.findByTypeIn(Arrays.asList("driver", "passenger"));

        List<Location> all = new ArrayList<>();
        all.addAll(vehicles);
        all.addAll(commuters);

        log.warn("Getting latest locations for user: {}", userId);
        log.info("Found {} vehicles and {} commuters", vehicles.size(), commuters.size());

        if (all.isEmpty()) {
            log.warn("No locations available for vehicles, drivers or passengers");
            return Collections.emptyList();
        }

        Map<String, Location> latest = new HashMap<>();
        for (Location loc : all) {
            Location existing = latest.get(loc.getUserId());
            if (existing == null || loc.getTimestamp() > existing.getTimestamp()) {
                latest.put(loc.getUserId(), loc);
            }
        }
        return new ArrayList<>(latest.values());
    }
}
