package com.ideabotkey.transportrouting.service;

import com.google.transit.realtime.GtfsRealtime.FeedEntity;
import com.google.transit.realtime.GtfsRealtime.FeedMessage;
import com.google.transit.realtime.GtfsRealtime.VehiclePosition;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GtfsService {

    private final String gtfsUrl = "https://YOUR_AGENCY_URL/vehiclePositions.pb"; // Replace with real feed URL

    public List<VehiclePosition> fetchVehicles() throws IOException {
        URL url = new URL(gtfsUrl);
        try (InputStream in = url.openStream()) {
            FeedMessage feed = FeedMessage.parseFrom(in);
            return feed.getEntityList().stream()
                    .filter(FeedEntity::hasVehicle)
                    .map(FeedEntity::getVehicle)
                    .collect(Collectors.toList());
        }
    }
}
