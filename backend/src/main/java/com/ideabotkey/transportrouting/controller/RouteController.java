package com.ideabotkey.transportrouting.controller;

import com.ideabotkey.transportrouting.model.Route;
import com.ideabotkey.transportrouting.model.Trip;
import com.ideabotkey.transportrouting.repository.RouteRepository;
import com.ideabotkey.transportrouting.repository.TripRepository;
import com.ideabotkey.transportrouting.repository.UserRepository;
import com.ideabotkey.transportrouting.service.PermissionChecker;
import com.ideabotkey.transportrouting.dto.RouteStatsDto;
import com.ideabotkey.transportrouting.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/routes")
public class RouteController extends BaseController {

    private final RouteRepository repository;
    private final TripRepository tripRepository;

    public RouteController(RouteRepository repository, UserRepository userRepository, PermissionChecker checker,
            TripRepository tripRepository) {
        super(userRepository, checker);
        this.repository = repository;
        this.tripRepository = tripRepository;
    }

    @GetMapping
    public List<Route> all(@RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "VIEW_ROUTE");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Route get(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "VIEW_ROUTE");
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Route not found"));
    }

    @PostMapping
    public Route create(@RequestBody Route route, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "CREATE_ROUTE");
        return repository.save(route);
    }

    @PutMapping("/{id}")
    public Route update(@PathVariable Long id, @RequestBody Route route, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "UPDATE_ROUTE");
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Route not found");
        }
        route.setId(id);
        return repository.save(route);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "DELETE_ROUTE");
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Route not found");
        }
        repository.deleteById(id);
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<Void> deleteMultipleRoutes(@RequestBody List<Long> ids,
            @RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "DELETE_ROUTE");
        ids.forEach(id -> {
            if (repository.existsById(id)) {
                repository.deleteById(id);
            }
        });
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stats")
    public List<RouteStatsDto> getRouteStats(@RequestHeader("X-User-Id") Long userId) {
        checkPermission(userId, "VIEW_ROUTE"); // Use your permission check if required

        List<Route> routes = repository.findAll();
        List<Trip> trips = tripRepository.findAll();

        List<RouteStatsDto> stats = new ArrayList<>();

        for (Route route : routes) {
            RouteStatsDto dto = new RouteStatsDto();
            dto.routeName = route.getName();
            dto.status = route.getStatus();

            // Count trips for this route
            int totalTrips = (int) trips.stream().filter(t -> t.getRoute().getId().equals(route.getId())).count();
            dto.totalTrips = totalTrips;

            // Utilization: fake for now (could be based on trip count or another logic)
            // Example: utilization = (totalTrips * 4) % 100, or random for demo
            dto.utilization = Math.min(95, 40 + totalTrips * 3);

            stats.add(dto);
        }

        return stats;
    }

}
