package com.ideabotkey.transportrouting.controller;

import com.ideabotkey.transportrouting.model.User;
import com.ideabotkey.transportrouting.model.Role;
import com.ideabotkey.transportrouting.model.Permission;
import com.ideabotkey.transportrouting.dto.UserLoginDto;
import com.ideabotkey.transportrouting.repository.UserRepository;
import com.ideabotkey.transportrouting.repository.RoleRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ideabotkey.transportrouting.exception.ResourceNotFoundException;
import com.ideabotkey.transportrouting.exception.UnauthorizedException;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserRepository repository, RoleRepository roleRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;
    }

    private UserLoginDto toDto(User user) {
        UserLoginDto dto = new UserLoginDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        if (user.getRoles() != null) {
            Set<String> roleNames = user.getRoles().stream()
                    .map(Role::getName)
                    .collect(Collectors.toCollection(HashSet::new));
            dto.setRoles(roleNames);
        }
        return dto;
    }

    @GetMapping
    public List<User> all() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<User> get(@PathVariable Long id) {
        User user = repository.findWithRolesById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        user.getRoles().forEach(r -> Hibernate.initialize(r.getPermissions()));
        Hibernate.initialize(user.getPermissions());

        return ResponseEntity.ok(user);
    }

    @PostMapping
    public User create(@RequestBody User user) {
        user.setPassword(user.getPassword());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return repository.save(user);
    }

    @PutMapping("/{id}/roles")
    public ResponseEntity<User> assignRoles(@PathVariable Long id, @RequestBody List<Long> roleIds) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<Role> roles = roleRepository.findAllById(roleIds);
        user.setRoles(new HashSet<>(roles));  // ✅ FIX: convert List to Set
        return ResponseEntity.ok(repository.save(user));
    }

    @PutMapping("/{id}/permissions")
    public ResponseEntity<User> assignPermissions(@PathVariable Long id, @RequestBody List<Long> permissionIds) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        EnumSet<Permission> perms = EnumSet.noneOf(Permission.class);
        permissionIds.forEach(pid -> perms.add(Permission.fromId(pid)));
        user.setPermissions(perms);
        return ResponseEntity.ok(repository.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        return repository.findById(id)
                .map(existing -> {
                    user.setId(existing.getId());
                    if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                        user.setPassword(user.getPassword());
                    } else {
                        user.setPassword(existing.getPassword());
                    }
                    user.setCreatedAt(existing.getCreatedAt());
                    user.setUpdatedAt(LocalDateTime.now());
                    return ResponseEntity.ok(repository.save(user));
                })
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }
        repository.deleteById(id);
    }

    // @PostMapping("/login")
    // public ResponseEntity<UserLoginDto> login(@RequestBody LoginRequest request) {
    //     logger.info("Fetching user  {}", request.getUsername());
    //     User user = repository.findByUsername(request.getUsername())
    //             .or(() -> repository.findByEmail(request.getUsername()))
    //             .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));

    //     if (!user.getPassword().equals(request.getPassword())) {
    //         throw new UnauthorizedException("Invalid username or password");
    //     }

    //     Hibernate.initialize(user.getRoles());
    //     user.getRoles().forEach(r -> Hibernate.initialize(r.getPermissions()));
    //     Hibernate.initialize(user.getPermissions());

    //     return ResponseEntity.ok(toDto(user));
    // }



@PostMapping("/login")
public ResponseEntity<UserLoginDto> login(@RequestBody LoginRequest request) {
    logger.info("🔐 Attempting login with username/email: {}", request.getUsername());

    // Fetch by username or email
    User user = repository.findByUsername(request.getUsername())
            .or(() -> repository.findByEmail(request.getUsername()))
            .orElseThrow(() -> {
                logger.warn("❌ User not found: {}", request.getUsername());
                return new UnauthorizedException("Invalid username or password");
            });

    // Check status
    if (!"ACTIVE".equalsIgnoreCase(user.getStatus())) {
        logger.warn("⛔ Inactive user: {}", user.getUsername());
        throw new UnauthorizedException("Account not active");
    }

    // Password check (plain text - WARNING: insecure!)
    if (!user.getPassword().equals(request.getPassword())) {
        logger.warn("❌ Invalid password for user: {}", user.getUsername());
        throw new UnauthorizedException("Invalid username or password");
    }

    logger.info("✅ Login success for user: {}", user.getUsername());

    Hibernate.initialize(user.getRoles());
    user.getRoles().forEach(r -> Hibernate.initialize(r.getPermissions()));
    Hibernate.initialize(user.getPermissions());

    return ResponseEntity.ok(toDto(user));
}

}

