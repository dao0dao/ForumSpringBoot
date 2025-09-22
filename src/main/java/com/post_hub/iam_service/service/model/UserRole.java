package com.post_hub.iam_service.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {
    SUPER_ADMIN("SUPER_ADMIN"),
    ADMIN("ADMIN"),
    USER("USER");

    private final String role;
}
