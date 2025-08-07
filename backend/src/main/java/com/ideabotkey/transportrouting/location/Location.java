package com.ideabotkey.transportrouting.location;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    /**
     * Denotes the source entity for this coordinate.
     * Expected values are "vehicle", "driver" or "passenger".
     */
    private String type;
    private double latitude;
    private double longitude;
    private long timestamp;
}
