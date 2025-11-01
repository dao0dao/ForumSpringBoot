package com.post_hub.iam_service.service;

import com.post_hub.iam_service.model.dto.user.UserDTO;
import com.post_hub.iam_service.model.request.user.NewUserRequest;

import jakarta.annotation.Nonnull;

public interface UserService {
    UserDTO getById (@Nonnull Integer userId);
    UserDTO createUser(@Nonnull NewUserRequest newUserRequest);
}
