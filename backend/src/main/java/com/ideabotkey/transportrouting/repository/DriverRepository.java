package com.ideabotkey.transportrouting.repository;

import com.ideabotkey.transportrouting.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Long> {
}
