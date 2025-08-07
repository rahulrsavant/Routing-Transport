package com.ideabotkey.transportrouting.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ideabotkey.transportrouting.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    // Fetch users and their roles when retrieving a single company.
    // This doesn't trigger the MultipleBagFetchException because only
    // one root entity is loaded.
    @EntityGraph(attributePaths = {"users", "users.roles"})
    Optional<Company> findWithUsersById(Long id);

    // When loading all companies we only join fetch the users. Fetching
    // users and their roles in one query leads to MultipleBagFetchException
    // in Hibernate. Roles will be lazy-loaded when converting to DTOs.
    @Query("SELECT DISTINCT c FROM Company c LEFT JOIN FETCH c.users")
//    @EntityGraph(attributePaths = {"users"})
    List<Company> findAllWithUsers();
}
