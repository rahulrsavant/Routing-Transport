package com.ideabotkey.transportrouting.location;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByType(String type);

    List<Location> findByTypeIn(Collection<String> types);
}
