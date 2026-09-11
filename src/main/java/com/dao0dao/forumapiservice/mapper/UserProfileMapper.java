package com.dao0dao.forumapiservice.mapper;

import com.dao0dao.forumapiservice.model.dto.user.UserProfileDTO;
import com.dao0dao.forumapiservice.model.entities.UserEntity;
import com.dao0dao.forumapiservice.model.enums.UserRole;
import com.dao0dao.forumapiservice.security.model.CustomUserDetails;

public class UserProfileMapper {

    public static UserProfileDTO toDto(UserEntity user) {
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
