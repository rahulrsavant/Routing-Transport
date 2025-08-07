package com.ideabotkey.transportrouting.repository;

import com.ideabotkey.transportrouting.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByVehicleId(Long vehicleId);
}
