package com.post_hub.iam_service.service;

import com.post_hub.iam_service.model.dto.user.UserDTO;
import com.post_hub.iam_service.model.response.ApiResponse;

import jakarta.annotation.Nonnull;

public interface UserService {
    ApiResponse<UserDTO> getById (@Nonnull Integer userId);
}
