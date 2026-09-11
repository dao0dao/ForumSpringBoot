package com.dao0dao.forumapiservice.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.dao0dao.forumapiservice.model.dto.user.UserDTO;
import com.dao0dao.forumapiservice.model.entities.UserEntity;
import com.dao0dao.forumapiservice.model.enums.UserRole;
import com.dao0dao.forumapiservice.model.request.user.NewUserRequest;

public class UserMapper {
    public static UserDTO toDTO(UserEntity user) {
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
                .registration_status(user.getRegistrationStatus())
                .roles(roles)
                .updated(user.getUpdated())
                .username(user.getUsername())
                .build();
    }

    public static UserEntity toEntity(NewUserRequest request) {
        if (request == null) {
            return null;
        }
        return UserEntity.builder()
                .email(request.getEmail().toLowerCase())
                .password(request.getPassword())
                .username(request.getUsername().toLowerCase())
                .build();
    }

    public static UserEntity toEntity(String email, String password) {
        return UserEntity.builder()
                .email(email)
                .password(password)
                .username(email)
                .build();
    }
}
