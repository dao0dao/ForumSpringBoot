package com.post_hub.iam_service.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {
    SUPER_ADMIN("SUPER_ADMIN"),
    ADMIN("ADMIN"),
    USER("USER");

    private final String role;

    public static UserRole fromName (String name){
        try{
            return UserRole.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            return UserRole.USER;
        }
    }
}
