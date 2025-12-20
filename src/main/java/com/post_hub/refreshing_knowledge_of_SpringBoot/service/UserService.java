package com.post_hub.refreshing_knowledge_of_SpringBoot.service;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.user.UserDTO;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.request.user.NewUserRequest;

import jakarta.annotation.Nonnull;

public interface UserService {
    UserDTO getById (@Nonnull Integer userId);
    UserDTO createUser(@Nonnull NewUserRequest newUserRequest);
}
