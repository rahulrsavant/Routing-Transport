package com.ideabotkey.transportrouting.repository;

import com.ideabotkey.transportrouting.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {
}
