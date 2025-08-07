package com.ideabotkey.transportrouting.controller;

import com.ideabotkey.transportrouting.model.TripEmployee;
import com.ideabotkey.transportrouting.repository.TripEmployeeRepository;
import com.ideabotkey.transportrouting.repository.UserRepository;
import com.ideabotkey.transportrouting.service.PermissionChecker;
import com.ideabotkey.transportrouting.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trip-employees")
public class TripEmployeeController extends BaseController {

    private final TripEmployeeRepository repository;

    public TripEmployeeController(TripEmployeeRepository repository, UserRepository userRepository, PermissionChecker checker) {
        super(userRepository, checker);
        this.repository = repository;
    }

    @GetMapping
    public List<TripEmployee> all(@RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "VIEW_TRIP");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public TripEmployee get(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "VIEW_TRIP");
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TripEmployee not found"));
    }

    @PostMapping
    public TripEmployee create(@RequestBody TripEmployee tripEmployee, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "CREATE_TRIP");
        return repository.save(tripEmployee);
    }

    @PutMapping("/{id}")
    public TripEmployee update(@PathVariable Long id, @RequestBody TripEmployee tripEmployee, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "UPDATE_TRIP");
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("TripEmployee not found");
        }
        tripEmployee.setId(id);
        return repository.save(tripEmployee);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "DELETE_TRIP");
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("TripEmployee not found");
        }
        repository.deleteById(id);
    }
}
