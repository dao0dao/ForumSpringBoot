package com.post_hub.iam_service.service;

import com.post_hub.iam_service.service.models.AuthResult;

public interface AuthorizationsService {
    AuthResult loginUser(String email, String password);

    Boolean registerUser(String email, String password);
}
