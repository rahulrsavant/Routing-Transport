// package com.ideabotkey.transportrouting.service;

// import com.ideabotkey.transportrouting.dto.LocationResponse;
// import com.ideabotkey.transportrouting.dto.LocationUpdateRequest;
// import com.ideabotkey.transportrouting.exception.ResourceNotFoundException;
// import com.ideabotkey.transportrouting.model.VehicleLocation;
// import com.ideabotkey.transportrouting.repository.EmployeeRepository;
// import com.ideabotkey.transportrouting.repository.TripRepository;
// import com.ideabotkey.transportrouting.repository.VehicleLocationRepository;
// import org.springframework.stereotype.Service;

// import java.time.Instant;
// import java.util.Optional;

// @Service
// public class LocationService {
// private final VehicleLocationRepository repository;
// private final TripRepository tripRepository;
// private final EmployeeRepository employeeRepository;

// public LocationService(VehicleLocationRepository repository, TripRepository
// tripRepository, EmployeeRepository employeeRepository) {
// this.repository = repository;
// this.tripRepository = tripRepository;
// this.employeeRepository = employeeRepository;
// }

// public void saveLocation(LocationUpdateRequest request) {
// VehicleLocation location = new VehicleLocation();
// location.setTrip(tripRepository.findById(request.getTripId())
// .orElseThrow(() -> new ResourceNotFoundException("Trip not found")));
// if (!request.isDriver() && request.getEmployeeId() != null) {
// employeeRepository.findById(request.getEmployeeId()).ifPresent(location::setEmployee);
// }
// location.setDriver(request.isDriver());
// location.setLatitude(request.getLatitude());
// location.setLongitude(request.getLongitude());
// location.setTimestamp(request.getTimestamp());
// repository.save(location);
// }

// public Optional<LocationResponse> getCurrentLocation(Long tripId) {
// Instant cutoff = Instant.now().minusSeconds(180); // 3 minutes
// Optional<VehicleLocation> driver = repository
// .findTopByTripIdAndIsDriverOrderByTimestampDesc(tripId, true);
// if (driver.isPresent() && driver.get().getTimestamp().isAfter(cutoff)) {
// VehicleLocation d = driver.get();
// return Optional.of(new LocationResponse("driver", d.getLatitude(),
// d.getLongitude(), d.getTimestamp(), null));
// }

// return repository.findTopByTripIdAndIsDriverOrderByTimestampDesc(tripId,
// false)
// .map(loc -> new LocationResponse("employee", loc.getLatitude(),
// loc.getLongitude(), loc.getTimestamp(),
// loc.getEmployee() != null ? loc.getEmployee().getId() : null));
// }
// }
