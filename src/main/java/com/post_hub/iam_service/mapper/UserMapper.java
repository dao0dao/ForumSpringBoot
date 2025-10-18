package com.post_hub.iam_service.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.post_hub.iam_service.model.dto.user.UserDTO;
import com.post_hub.iam_service.model.entities.User;
import com.post_hub.iam_service.model.enums.UserRole;
import com.post_hub.iam_service.model.request.user.NewUserRequest;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        List<UserRole> roles = null;
        if (user.getRoles() != null) {
            roles = user.getRoles().stream().map(role -> RoleMapper.toUserRole(role)).collect(Collectors.toList());
        }

        return UserDTO.builder()
                .created(user.getCreated())
                .deleted(user.getDeleted())
                .id(user.getId())
                .lastLogin(user.getLastLogin())
                .registration_status(user.getRegistration_status())
                .roles(roles)
                .updated(user.getUpdated())
                .username(user.getUsername())
                .build();
    }

    public static User toEntity(NewUserRequest request) {
        if (request == null) {
            return null;
        }
        return User.builder()
                .email(request.getEmail().toLowerCase())
                .password(request.getPassword())
                .username(request.getUsername().toLowerCase())
                .build();
    }
}
