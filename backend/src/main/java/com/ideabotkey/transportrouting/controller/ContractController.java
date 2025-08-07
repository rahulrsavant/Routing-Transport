package com.ideabotkey.transportrouting.controller;

import com.ideabotkey.transportrouting.model.Contract;
import com.ideabotkey.transportrouting.repository.ContractRepository;
import com.ideabotkey.transportrouting.repository.UserRepository;
import com.ideabotkey.transportrouting.service.PermissionChecker;
import com.ideabotkey.transportrouting.dto.ContractDTO;
import com.ideabotkey.transportrouting.dto.ContractConverter;
import com.ideabotkey.transportrouting.exception.ResourceNotFoundException;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/contracts")
public class ContractController extends BaseController {

    private final ContractRepository repository;

    public ContractController(ContractRepository repository, UserRepository userRepository, PermissionChecker checker) {
        super(userRepository, checker);
        this.repository = repository;
    }

    @GetMapping
    public List<ContractDTO> all(@RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "VIEW_CONTRACT");
        return repository.findAll()
                .stream()
                .map(ContractConverter::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public Contract create(@RequestBody ContractDTO dto, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "CREATE_CONTRACT");
        Contract contract = ContractConverter.toEntity(dto);
        return repository.save(contract);
    }

    @GetMapping("/{id}")
    public ContractDTO get(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "VIEW_CONTRACT");
        Contract contract = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found"));
        return ContractConverter.toDTO(contract);
    }

    @PutMapping("/{id}")
    public Contract update(@PathVariable Long id, @RequestBody ContractDTO dto,
            @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "UPDATE_CONTRACT");
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Contract not found");
        }
        Contract contract = ContractConverter.toEntity(dto);
        contract.setId(id);
        return repository.save(contract);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "DELETE_CONTRACT");
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Contract not found");
        }
        repository.deleteById(id);
    }
}
