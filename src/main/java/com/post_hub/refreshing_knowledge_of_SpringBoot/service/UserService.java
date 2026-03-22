package com.post_hub.refreshing_knowledge_of_SpringBoot.service;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.user.UserDTO;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.request.user.NewUserRequest;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.request.user.UpdateUserRequest;

import jakarta.annotation.Nonnull;

public interface UserService {
    UserDTO getById (@Nonnull Integer userId);
    UserDTO createUser(@Nonnull NewUserRequest newUserRequest);
    UserDTO updateUser(@Nonnull int userId, @Nonnull UpdateUserRequest newUserRequest);
}
