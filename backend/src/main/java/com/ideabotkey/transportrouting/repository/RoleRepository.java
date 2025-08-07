package com.ideabotkey.transportrouting.repository;

import com.ideabotkey.transportrouting.model.Role;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @EntityGraph(attributePaths = "permissions")
    List<Role> findAll();

    @EntityGraph(attributePaths = "permissions")
    Optional<Role> findById(Long id);
}
