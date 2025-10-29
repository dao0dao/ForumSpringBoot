package com.post_hub.iam_service.service.models;

import com.post_hub.iam_service.model.dto.user.UserProfileDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResult {
    private UserProfileDTO userProfile;
    private String token;
}
