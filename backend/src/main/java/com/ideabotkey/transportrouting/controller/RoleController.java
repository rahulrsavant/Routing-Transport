package com.ideabotkey.transportrouting.controller;

import com.ideabotkey.transportrouting.model.Role;
import com.ideabotkey.transportrouting.model.Permission;
import com.ideabotkey.transportrouting.repository.RoleRepository;
import com.ideabotkey.transportrouting.repository.UserRepository;
import com.ideabotkey.transportrouting.service.PermissionChecker;
import com.ideabotkey.transportrouting.exception.ResourceNotFoundException;
import com.ideabotkey.transportrouting.dto.RoleDTO;
import com.ideabotkey.transportrouting.dto.PermissionDTO;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.EnumSet;
import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController extends BaseController {

    private final RoleRepository repository;

    public RoleController(RoleRepository repository,
                          UserRepository userRepository, PermissionChecker checker) {
        super(userRepository, checker);
        this.repository = repository;
    }

    @GetMapping
    public List<Role> all(@RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "VIEW_ROLE");
        return repository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> get(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "VIEW_ROLE");
        Role role = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        return ResponseEntity.ok(mapToDto(role));
    }

    @PostMapping
    public Role create(@RequestBody Role role, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "CREATE_ROLE");
        return repository.save(role);
    }

    @PutMapping("/{id}")
    public Role update(@PathVariable Long id, @RequestBody Role role, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "UPDATE_ROLE");
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Role not found");
        }
        role.setId(id);
        return repository.save(role);
    }

    @PutMapping("/{id}/permissions")
    public Role assignPermissions(@PathVariable Long id, @RequestBody List<Long> permissionIds,
                                  @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "UPDATE_ROLE");
        Role role = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        EnumSet<Permission> perms = EnumSet.noneOf(Permission.class);
        permissionIds.forEach(pid -> perms.add(Permission.fromId(pid)));
        role.setPermissions(perms);
        return repository.save(role);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "DELETE_ROLE");
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Role not found");
        }
        repository.deleteById(id);
    }

    private RoleDTO mapToDto(Role role) {
        List<PermissionDTO> perms = role.getPermissions().stream()
                .map(p -> new PermissionDTO(p.getId(), p.getCode()))
                .toList();
        return new RoleDTO(role.getId(), role.getName(), role.getDescription(), perms);
    }
}
