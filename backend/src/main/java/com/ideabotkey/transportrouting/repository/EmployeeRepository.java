package com.ideabotkey.transportrouting.repository;

import com.ideabotkey.transportrouting.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
