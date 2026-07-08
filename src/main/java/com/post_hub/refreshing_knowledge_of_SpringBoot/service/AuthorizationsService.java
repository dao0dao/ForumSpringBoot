package com.post_hub.refreshing_knowledge_of_SpringBoot.service;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.user.UserProfileDTO;
import com.post_hub.refreshing_knowledge_of_SpringBoot.service.models.AuthResult;

public interface AuthorizationsService {
    AuthResult loginUser(String email, String password);

    Boolean registerUser(String email, String password, String confirmPassword);

    UserProfileDTO getCurrentUser();

    String refreshToken();

    void logoutUser();
}
