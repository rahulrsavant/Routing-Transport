package com.ideabotkey.transportrouting.repository;

import com.ideabotkey.transportrouting.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {
    boolean existsByDriverId(Long driverId);
}
