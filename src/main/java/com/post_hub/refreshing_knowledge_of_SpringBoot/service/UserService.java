package com.post_hub.refreshing_knowledge_of_SpringBoot.service;

import org.springframework.lang.NonNull;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.user.UserDTO;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.request.user.NewUserRequest;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.request.user.UpdateUserRequest;


public interface UserService {
    UserDTO getById (@NonNull Integer userId);
    UserDTO createUser(@NonNull NewUserRequest newUserRequest);
    UserDTO updateUser(@NonNull Integer userId, @NonNull UpdateUserRequest newUserRequest);
}
