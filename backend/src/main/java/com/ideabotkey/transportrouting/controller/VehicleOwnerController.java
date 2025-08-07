package com.ideabotkey.transportrouting.controller;

import com.ideabotkey.transportrouting.model.VehicleOwner;
import com.ideabotkey.transportrouting.repository.VehicleOwnerRepository;
import com.ideabotkey.transportrouting.repository.UserRepository;
import com.ideabotkey.transportrouting.service.PermissionChecker;
import com.ideabotkey.transportrouting.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/owners")
public class VehicleOwnerController extends BaseController {

    private final VehicleOwnerRepository repository;

    public VehicleOwnerController(VehicleOwnerRepository repository, UserRepository userRepository, PermissionChecker checker) {
        super(userRepository, checker);
        this.repository = repository;
    }

    @GetMapping
    public List<VehicleOwner> all(@RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "VIEW_VEHICLE");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public VehicleOwner get(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "VIEW_VEHICLE");
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));
    }

    @PostMapping
    public VehicleOwner create(@RequestBody VehicleOwner owner, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "CREATE_VEHICLE");
        return repository.save(owner);
    }

    @PutMapping("/{id}")
    public VehicleOwner update(@PathVariable Long id, @RequestBody VehicleOwner owner, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "UPDATE_VEHICLE");
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Owner not found");
        }
        owner.setId(id);
        return repository.save(owner);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "DELETE_VEHICLE");
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Owner not found");
        }
        repository.deleteById(id);
    }
}
