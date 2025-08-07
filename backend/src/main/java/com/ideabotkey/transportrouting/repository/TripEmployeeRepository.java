package com.ideabotkey.transportrouting.repository;

import com.ideabotkey.transportrouting.model.TripEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripEmployeeRepository extends JpaRepository<TripEmployee, Long> {
}
