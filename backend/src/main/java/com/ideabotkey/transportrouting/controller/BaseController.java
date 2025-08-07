package com.ideabotkey.transportrouting.controller;

import com.ideabotkey.transportrouting.exception.ForbiddenException;
import com.ideabotkey.transportrouting.model.User;
import com.ideabotkey.transportrouting.repository.UserRepository;
import com.ideabotkey.transportrouting.service.PermissionChecker;

public abstract class BaseController {
    private final UserRepository userRepository;
    private final PermissionChecker permissionChecker;

    protected BaseController(UserRepository userRepository, PermissionChecker permissionChecker) {
        this.userRepository = userRepository;
        this.permissionChecker = permissionChecker;
    }

    protected User requireUser(Long userId) {
        return userRepository.findWithRolesById(userId)
                .or(() -> userRepository.findById(userId))
                .orElseThrow(() -> new ForbiddenException("Invalid user"));
    }

    protected void checkPermission(Long userId, String permission) {
        User user = requireUser(userId);
        if (!permissionChecker.hasPermission(user, permission)) {
            throw new ForbiddenException("You do not have permission to " + permission.toLowerCase().replace('_', ' ').toLowerCase());
        }
    }
}
