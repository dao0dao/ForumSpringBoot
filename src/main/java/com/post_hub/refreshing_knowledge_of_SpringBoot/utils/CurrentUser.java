package com.post_hub.refreshing_knowledge_of_SpringBoot.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.exception.NoAuthorizationException;
import com.post_hub.refreshing_knowledge_of_SpringBoot.security.model.CustomUserDetails;

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

    public static boolean isActive(){
        return getUserDetails().isEnabled();
    }
}
