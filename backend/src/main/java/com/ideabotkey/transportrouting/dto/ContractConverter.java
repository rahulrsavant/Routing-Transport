package com.ideabotkey.transportrouting.dto;

import com.ideabotkey.transportrouting.model.*;

public class ContractConverter {

    public static ContractDTO toDTO(Contract contract) {
        ContractDTO dto = new ContractDTO();
        dto.setId(contract.getId());
        dto.setContractType(contract.getContractType());
        dto.setAmount(contract.getAmount());
        dto.setStatus(contract.getStatus());
        dto.setStartDate(contract.getStartDate());
        dto.setEndDate(contract.getEndDate());

        if (contract.getCompany() != null) {
            dto.setCompanyId(contract.getCompany().getId());
            dto.setCompanyName(contract.getCompany().getName());
        }

        if (contract.getOwner() != null) {
            dto.setOwnerId(contract.getOwner().getId());
            dto.setOwnerName(contract.getOwner().getName());
        }

        if (contract.getVehicle() != null) {
            dto.setVehicleId(contract.getVehicle().getId());
            dto.setVehicleNumber(contract.getVehicle().getRegistrationNumber());
        }

        return dto;
    }

    public static Contract toEntity(ContractDTO dto) {
        Contract contract = new Contract();
        contract.setId(dto.getId());
        contract.setContractType(dto.getContractType());
        contract.setAmount(dto.getAmount());
        contract.setStatus(dto.getStatus());
        contract.setStartDate(dto.getStartDate());
        contract.setEndDate(dto.getEndDate());

        if (dto.getCompanyId() != null) {
            Company company = new Company();
            company.setId(dto.getCompanyId());
            contract.setCompany(company);
        }

        if (dto.getOwnerId() != null) {
            VehicleOwner owner = new VehicleOwner();
            owner.setId(dto.getOwnerId());
            contract.setOwner(owner);
        }

        if (dto.getVehicleId() != null) {
            Vehicle vehicle = new Vehicle();
            vehicle.setId(dto.getVehicleId());
            contract.setVehicle(vehicle);
        }

        return contract;
    }
}
