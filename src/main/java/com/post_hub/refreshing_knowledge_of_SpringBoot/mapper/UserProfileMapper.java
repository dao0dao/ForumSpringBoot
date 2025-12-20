package com.post_hub.refreshing_knowledge_of_SpringBoot.mapper;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.user.UserProfileDTO;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.entities.User;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.enums.UserRole;
import com.post_hub.refreshing_knowledge_of_SpringBoot.security.model.CustomUserDetails;

public class UserProfileMapper {

    public static UserProfileDTO toDto(User user) {
        return UserProfileDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .userRoles(user.getRoles().stream()
                        .map(role -> RoleMapper.toUserRole(role))
                        .toList())
                .build();
    }

    public static UserProfileDTO toDto(CustomUserDetails userDetails) {
        return UserProfileDTO.builder()
                .username(userDetails.getUsername())
                .email(userDetails.getUserEmail())
                .userRoles(userDetails.getAuthorities().stream()
                        .map(authority -> UserRole.fromName(authority.getAuthority())).toList())
                .build();
    }
}
