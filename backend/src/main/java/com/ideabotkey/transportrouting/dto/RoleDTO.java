package com.ideabotkey.transportrouting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import com.ideabotkey.transportrouting.dto.PermissionDTO;

/**
 * DTO representing a role with its permissions.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private Long id;
    private String name;
    private String description;
    private List<PermissionDTO> permissions;
}
