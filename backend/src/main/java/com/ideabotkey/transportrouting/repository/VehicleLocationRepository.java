package com.ideabotkey.transportrouting.repository;

import com.ideabotkey.transportrouting.model.VehicleLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleLocationRepository extends JpaRepository<VehicleLocation, Long> {
    Optional<VehicleLocation> findTopByTripIdAndIsDriverOrderByTimestampDesc(Long tripId, boolean isDriver);
}
