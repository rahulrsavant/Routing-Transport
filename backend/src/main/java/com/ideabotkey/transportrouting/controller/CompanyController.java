package com.ideabotkey.transportrouting.controller;

import com.ideabotkey.transportrouting.model.Company;
import com.ideabotkey.transportrouting.model.User;
import com.ideabotkey.transportrouting.model.Role;
import com.ideabotkey.transportrouting.repository.CompanyRepository;
import com.ideabotkey.transportrouting.repository.UserRepository;
import com.ideabotkey.transportrouting.service.PermissionChecker;
import com.ideabotkey.transportrouting.exception.ResourceNotFoundException;
import com.ideabotkey.transportrouting.dto.CompanyDto;
import com.ideabotkey.transportrouting.dto.UserDto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController extends BaseController {

    private final CompanyRepository repository;

    public CompanyController(CompanyRepository repository, UserRepository userRepository, PermissionChecker checker) {
        super(userRepository, checker);
        this.repository = repository;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<CompanyDto> all(@RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "VIEW_COMPANY");
        return repository.findAllWithUsers().stream()
                .map(this::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public Company get(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "VIEW_COMPANY");
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
    }

    @PostMapping
    public Company create(@RequestBody Company company, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "CREATE_COMPANY");
        return repository.save(company);
    }

    @PutMapping("/{id}")
    public Company update(@PathVariable Long id, @RequestBody Company company, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "UPDATE_COMPANY");
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Company not found");
        }
        company.setId(id);
        return repository.save(company);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "DELETE_COMPANY");
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Company not found");
        }
        repository.deleteById(id);
    }

    private CompanyDto toDto(Company company) {
        CompanyDto dto = new CompanyDto();
        dto.setId(company.getId());
        dto.setName(company.getName());
        dto.setAddress(company.getAddress());
        List<UserDto> userDtos = company.getUsers().stream()
                .map(this::toUserDto)
                .toList();
        dto.setUsers(userDtos);
        return dto;
    }

    private UserDto toUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        Set<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
        dto.setRoles(roles);
        return dto;
    }
}
