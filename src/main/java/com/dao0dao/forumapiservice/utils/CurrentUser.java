package com.dao0dao.forumapiservice.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.dao0dao.forumapiservice.model.enums.UserRole;
import com.dao0dao.forumapiservice.model.exception.NoAuthorizationException;
import com.dao0dao.forumapiservice.security.model.CustomUserDetails;

public class CurrentUser {

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static boolean isExist() {
        return CurrentUser.getAuthentication() != null;
    }

    public static CustomUserDetails getUserDetails() {
        if (!CurrentUser.isExist()) {
            throw new NoAuthorizationException("User not found");
        }
        CustomUserDetails userDetails = (CustomUserDetails) CurrentUser.getAuthentication().getPrincipal();
        return userDetails;
    }

    public static Integer getUserId() {
        return getUserDetails().getUserId();
    }

    public static boolean isActive() {
        return getUserDetails().isEnabled();
    }

    public static boolean isAdmin() {
        return getUserDetails().getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals(UserRole.ADMIN.getRole())
                        || role.getAuthority().equals(UserRole.SUPER_ADMIN.getRole()));
    }

    public static boolean isSuperAdmin() {
        return getUserDetails().getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals(UserRole.SUPER_ADMIN.getRole()));
    }
}
