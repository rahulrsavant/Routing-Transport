package com.ideabotkey.transportrouting.controller;

import com.ideabotkey.transportrouting.model.Vehicle;
import com.ideabotkey.transportrouting.repository.VehicleRepository;
import com.ideabotkey.transportrouting.repository.UserRepository;
import com.ideabotkey.transportrouting.service.PermissionChecker;
import com.ideabotkey.transportrouting.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController extends BaseController {

    private final VehicleRepository repository;

    public VehicleController(VehicleRepository repository, UserRepository userRepository, PermissionChecker checker) {
        super(userRepository, checker);
        this.repository = repository;
    }

    @GetMapping
    public List<Vehicle> all(@RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "VIEW_VEHICLE");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Vehicle get(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "VIEW_VEHICLE");
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
    }

    @PostMapping
    public Vehicle create(@RequestBody Vehicle vehicle, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "CREATE_VEHICLE");
        return repository.save(vehicle);
    }

    @PutMapping("/{id}")
    public Vehicle update(@PathVariable Long id, @RequestBody Vehicle vehicle, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "UPDATE_VEHICLE");
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicle not found");
        }
        vehicle.setId(id);
        return repository.save(vehicle);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "DELETE_VEHICLE");
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicle not found");
        }
        repository.deleteById(id);
    }
}
