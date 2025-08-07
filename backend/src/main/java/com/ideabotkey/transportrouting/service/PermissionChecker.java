package com.ideabotkey.transportrouting.service;

import org.springframework.stereotype.Service;
import com.ideabotkey.transportrouting.model.Permission;
import com.ideabotkey.transportrouting.model.User;

@Service
public class PermissionChecker {

    public boolean hasPermission(User user, String permissionName) {
        if (user == null || permissionName == null) {
            return false;
        }
        Permission perm;
        try {
            perm = Permission.fromCode(permissionName);
        } catch (IllegalArgumentException ex) {
            return false;
        }

        boolean viaRole = user.getRoles() != null &&
                user.getRoles().stream()
                        .anyMatch(r -> r.getPermissions().contains(perm));
        if (viaRole) {
            return true;
        }
        return user.getPermissions() != null &&
                user.getPermissions().contains(perm);
    }
}
