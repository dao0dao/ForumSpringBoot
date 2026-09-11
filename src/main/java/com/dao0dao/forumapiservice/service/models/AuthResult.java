package com.dao0dao.forumapiservice.service.models;

import com.dao0dao.forumapiservice.model.dto.user.UserProfileDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResult {
    private UserProfileDTO userProfile;
    private String token;
}
