package com.ideabotkey.transportrouting.controller;

import com.ideabotkey.transportrouting.dto.EmployeeDto;
import com.ideabotkey.transportrouting.model.Company;
import com.ideabotkey.transportrouting.model.Employee;
import com.ideabotkey.transportrouting.repository.EmployeeRepository;
import com.ideabotkey.transportrouting.repository.UserRepository;
import com.ideabotkey.transportrouting.service.PermissionChecker;
import com.ideabotkey.transportrouting.exception.ResourceNotFoundException;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController extends BaseController {

    private final EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository, UserRepository userRepository, PermissionChecker checker) {
        super(userRepository, checker);
        this.repository = repository;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<EmployeeDto> all(@RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "VIEW_EMPLOYEE");
        return repository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public EmployeeDto get(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "VIEW_EMPLOYEE");
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return toDto(employee);
    }

    @PostMapping
    public EmployeeDto create(@RequestBody EmployeeDto dto, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "CREATE_EMPLOYEE");
        Employee employee = repository.save(toEntity(dto));
        return toDto(employee);
    }

    @PutMapping("/{id}")
    public EmployeeDto update(@PathVariable Long id, @RequestBody EmployeeDto dto, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "UPDATE_EMPLOYEE");
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found");
        }
        Employee employee = toEntity(dto);
        employee.setId(id);
        employee = repository.save(employee);
        return toDto(employee);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "DELETE_EMPLOYEE");
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Record not found");
        }
        repository.deleteById(id);
    }

    private EmployeeDto toDto(Employee employee) {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setPhone(employee.getPhone());
        dto.setAddress(employee.getAddress());
        dto.setStatus(employee.getStatus());
        dto.setCreatedAt(employee.getCreatedAt());
        dto.setUpdatedAt(employee.getUpdatedAt());
        if (employee.getCompany() != null) {
            dto.setCompanyId(employee.getCompany().getId());
            dto.setCompanyName(employee.getCompany().getName());
        }
        return dto;
    }

    private Employee toEntity(EmployeeDto dto) {
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setAddress(dto.getAddress());
        employee.setStatus(dto.getStatus());
        employee.setCreatedAt(dto.getCreatedAt());
        employee.setUpdatedAt(dto.getUpdatedAt());
        if (dto.getCompanyId() != null) {
            Company company = new Company();
            company.setId(dto.getCompanyId());
            employee.setCompany(company);
        }
        return employee;
    }
    


}
