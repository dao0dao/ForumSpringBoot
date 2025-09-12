package com.post_hub.iam_service.mapper;

import com.post_hub.iam_service.model.dto.user.UserDTO;
import com.post_hub.iam_service.model.enteties.User;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        return UserDTO.builder()
                .created(user.getCreated())
                .deleted(user.getDeleted())
                .id(user.getId())
                .last_login(user.getLast_login())
                .password(user.getPassword())
                .registration_status(user.getRegistration_status())
                .updated(user.getUpdated())
                .username(user.getUsername())
                .build();
    }
}
