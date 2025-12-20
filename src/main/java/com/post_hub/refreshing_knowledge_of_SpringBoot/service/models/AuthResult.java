package com.post_hub.refreshing_knowledge_of_SpringBoot.service.models;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.user.UserProfileDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResult {
    private UserProfileDTO userProfile;
    private String token;
}
