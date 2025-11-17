package com.post_hub.iam_service.controller;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.post_hub.iam_service.model.constans.ApiLogMessage;
import com.post_hub.iam_service.model.dto.user.UserProfileDTO;
import com.post_hub.iam_service.model.request.login.LoginRequest;
import com.post_hub.iam_service.model.response.ApiResponse;
import com.post_hub.iam_service.security.model.constans.SecurityConstans;
import com.post_hub.iam_service.service.AuthorizationsService;
import com.post_hub.iam_service.service.models.AuthResult;
import com.post_hub.iam_service.utils.ApiUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping()
public class AuthorizationController {

    private final AuthorizationsService authorizationsService;

    @PostMapping("${end.points.login}")
    public ResponseEntity<ApiResponse<UserProfileDTO>> authorizeUser(@RequestBody LoginRequest loginRequest) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        AuthResult authResult = this.authorizationsService.loginUser(loginRequest.getEmail(),
                loginRequest.getPassword());

        ApiResponse<UserProfileDTO> body = ApiResponse.tokenCreateUpdated(authResult.getUserProfile());
        return ResponseEntity.ok()
                .header(SecurityConstans.AUTHORIZATION_HEADER, SecurityConstans.BEARER_PREFIX + authResult.getToken())
                .body(body);
    }
}
