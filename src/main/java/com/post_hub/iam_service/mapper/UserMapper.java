package com.post_hub.iam_service.mapper;

import com.post_hub.iam_service.model.dto.user.UserDTO;
import com.post_hub.iam_service.model.enteties.User;
import com.post_hub.iam_service.model.request.user.NewUserRequest;

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

    public static User toEntity(NewUserRequest request){
        if(request == null){
            return null;
        }
        return User.builder()
        .email(request.getEmail().toLowerCase())
        .password(request.getPassword())
        .username(request.getUsername().toLowerCase())
        .build();
    }
}
