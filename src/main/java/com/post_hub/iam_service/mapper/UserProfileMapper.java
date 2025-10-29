package com.post_hub.iam_service.mapper;

import com.post_hub.iam_service.model.dto.user.UserProfileDTO;
import com.post_hub.iam_service.model.entities.User;

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
}
