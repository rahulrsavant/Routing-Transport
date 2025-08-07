package com.ideabotkey.transportrouting.controller;

import com.ideabotkey.transportrouting.model.PermissionEntity;
import com.ideabotkey.transportrouting.repository.PermissionRepository;
import com.ideabotkey.transportrouting.repository.UserRepository;
import com.ideabotkey.transportrouting.service.PermissionChecker;
import com.ideabotkey.transportrouting.exception.ResourceNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/permissions")
public class PermissionController extends BaseController {

    private final PermissionRepository repository;

    public PermissionController(PermissionRepository repository, UserRepository userRepository, PermissionChecker checker) {
        super(userRepository, checker);
        this.repository = repository;
    }

    @GetMapping
    public List<PermissionEntity> all(@RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "VIEW_PERMISSION");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public PermissionEntity get(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "VIEW_PERMISSION");
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found"));
    }

    @PostMapping
    public PermissionEntity create(@RequestBody PermissionEntity permission,
                                   @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "CREATE_PERMISSION");
        return repository.save(permission);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PermissionEntity> updatePermission(
            @PathVariable Long id,
            @RequestBody PermissionEntity dto) {

        PermissionEntity permission = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id " + id));

        permission.setCode(dto.getCode());
        permission.setDescription(dto.getDescription());

        PermissionEntity updated = repository.save(permission);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermission(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId) {

        checkPermission(userId, "DELETE_PERMISSION");

        PermissionEntity permission = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id " + id));

        repository.delete(permission);

        return ResponseEntity.noContent().build(); // returns 204 No Content
    }




}

