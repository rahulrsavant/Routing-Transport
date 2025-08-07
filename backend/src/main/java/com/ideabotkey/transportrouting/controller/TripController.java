package com.ideabotkey.transportrouting.controller;

import com.ideabotkey.transportrouting.model.Trip;
import com.ideabotkey.transportrouting.repository.TripRepository;
import com.ideabotkey.transportrouting.repository.UserRepository;
import com.ideabotkey.transportrouting.service.PermissionChecker;
import com.ideabotkey.transportrouting.exception.ResourceNotFoundException;
import com.ideabotkey.transportrouting.dto.TripDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trips")
public class TripController extends BaseController {

    private final TripRepository repository;

    public TripController(TripRepository repository, UserRepository userRepository, PermissionChecker checker) {
        super(userRepository, checker);
        this.repository = repository;
    }

    @GetMapping
    public List<TripDto> all(@RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "VIEW_TRIP");
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public TripDto get(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "VIEW_TRIP");
        Trip trip = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found"));
        return toDto(trip);
    }

    @PostMapping
    public Trip create(@RequestBody Trip trip, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "CREATE_TRIP");
        return repository.save(trip);
    }

    @PutMapping("/{id}")
    public Trip update(@PathVariable Long id, @RequestBody Trip trip, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "UPDATE_TRIP");
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Trip not found");
        }
        trip.setId(id);
        return repository.save(trip);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "DELETE_TRIP");
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Trip not found");
        }
        repository.deleteById(id);
    }

    /**
     * Convert Trip entity to DTO with related route, vehicle and driver info.
     */
    private TripDto toDto(Trip trip) {
        TripDto dto = new TripDto();
        dto.setId(trip.getId());
        if (trip.getRoute() != null) {
            dto.setRouteName(trip.getRoute().getName());
            dto.setRouteId(trip.getRoute().getId()); // <-- New line!
        }
        if (trip.getVehicle() != null) {
            dto.setVehicleRegistration(trip.getVehicle().getRegistrationNumber());
        }
        if (trip.getDriver() != null) {
            dto.setDriverName(trip.getDriver().getName());
        }
        dto.setDate(trip.getDate());
        dto.setStartTime(trip.getStartTime());
        dto.setEndTime(trip.getEndTime());
        dto.setStatus(trip.getStatus());
        return dto;
    }

}
