package com.dao0dao.forumapiservice.service;

import com.dao0dao.forumapiservice.service.models.AuthResult;

public interface AuthorizationsService {
    AuthResult loginUser(String email, String password);

    Boolean registerUser(String email, String password, String confirmPassword);

    String refreshToken();

    void logoutUser();
}
