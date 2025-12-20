package com.post_hub.refreshing_knowledge_of_SpringBoot.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.constans.ApiLogMessage;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.user.UserProfileDTO;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.request.authorization.AuthorizationRequest;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.response.ApiResponse;
import com.post_hub.refreshing_knowledge_of_SpringBoot.security.model.constans.SecurityConstans;
import com.post_hub.refreshing_knowledge_of_SpringBoot.service.AuthorizationsService;
import com.post_hub.refreshing_knowledge_of_SpringBoot.service.models.AuthResult;
import com.post_hub.refreshing_knowledge_of_SpringBoot.utils.ApiUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping()
public class AuthorizationController {

    private final AuthorizationsService authorizationsService;

    @PostMapping("${end.points.login}")
    public ResponseEntity<ApiResponse<UserProfileDTO>> authorizeUser(@RequestBody AuthorizationRequest authRequest) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        AuthResult authResult = this.authorizationsService.loginUser(authRequest.getEmail(),
                authRequest.getPassword());

        ApiResponse<UserProfileDTO> body = ApiResponse.tokenCreateUpdated(authResult.getUserProfile());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,
                        ApiUtils.createCookie(SecurityConstans.JWT_COOKIE_NAME, authResult.getToken()))
                .body(body);
    }

    @PostMapping("${end.points.register}")
    public ResponseEntity<Void> registerUser(@RequestBody AuthorizationRequest authRequest) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        this.authorizationsService.registerUser(authRequest.getEmail(), authRequest.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("${end.points.token}${end.points.refresh}")
    public ResponseEntity<Void> getMethodName() {
        String token = this.authorizationsService.refreshToken();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, ApiUtils.createCookie(SecurityConstans.JWT_COOKIE_NAME, token)).build();
    }

}
