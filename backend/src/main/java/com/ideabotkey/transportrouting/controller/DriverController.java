package com.ideabotkey.transportrouting.controller;

import com.ideabotkey.transportrouting.model.Driver;
import com.ideabotkey.transportrouting.repository.DriverRepository;
import com.ideabotkey.transportrouting.repository.TripRepository;
import com.ideabotkey.transportrouting.repository.UserRepository;
import com.ideabotkey.transportrouting.service.PermissionChecker;
import org.springframework.http.ResponseEntity;
import com.ideabotkey.transportrouting.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/drivers")
public class DriverController extends BaseController {

    private final DriverRepository repository;
    private final TripRepository tripRepository;

    public DriverController(DriverRepository repository,
            TripRepository tripRepository,
            UserRepository userRepository,
            PermissionChecker checker) {
        super(userRepository, checker);
        this.repository = repository;
        this.tripRepository = tripRepository;
    }

    @GetMapping
    public List<Driver> all(@RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "VIEW_DRIVER");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Driver get(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "VIEW_DRIVER");
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));
    }

    @PostMapping
    public Driver create(@RequestBody Driver driver, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "CREATE_DRIVER");
        return repository.save(driver);
    }

    @PutMapping("/{id}")
    public Driver update(@PathVariable Long id, @RequestBody Driver driver, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "UPDATE_DRIVER");
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Driver not found");
        }
        driver.setId(id);
        return repository.save(driver);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "DELETE_DRIVER");
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Driver not found");
        }
        repository.deleteById(id);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deleteDrivers(@RequestBody List<Long> driverIds,
            @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "DELETE_DRIVER");

        // Map<String, Object> result = service.deleteDrivers(ids);

        // return ResponseEntity.ok(result);

        List<Long> deletableDrivers = new ArrayList<>();
        List<String> lockedDrivers = new ArrayList<>();

        for (Long id : driverIds) {
            if (tripRepository.existsByDriverId(id)) {
                lockedDrivers.add(tripRepository.getById(id).getDriver().getName());
            } else {
                deletableDrivers.add(id);
            }
        }

        if (!deletableDrivers.isEmpty()) {
            repository.deleteAllById(deletableDrivers);
        }

        String lockedDriverString = lockedDrivers.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));

        Map<String, Object> result = new HashMap<>();
        result.put("deleted", deletableDrivers);
        result.put("locked", lockedDrivers);
        String msg = String.format(
                "✅ Deleted %d drivers are : %s.\n❌ Skipped %d drivers engaged with trips/schedules are : %s.",
                deletableDrivers.size(), deletableDrivers, lockedDrivers.size(), lockedDriverString);
        result.put("message", msg);

        return ResponseEntity.ok(result);
    }
}
