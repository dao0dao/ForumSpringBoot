package com.dao0dao.forumapiservice.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dao0dao.forumapiservice.model.constans.ApiLogMessage;
import com.dao0dao.forumapiservice.model.dto.user.UserProfileDTO;
import com.dao0dao.forumapiservice.model.request.authorization.LoginRequest;
import com.dao0dao.forumapiservice.model.request.authorization.RegisterRequest;
import com.dao0dao.forumapiservice.model.response.ApiResponse;
import com.dao0dao.forumapiservice.security.annotation.ApiController;
import com.dao0dao.forumapiservice.security.model.constans.SecurityConstans;
import com.dao0dao.forumapiservice.service.AuthorizationsService;
import com.dao0dao.forumapiservice.service.models.AuthResult;
import com.dao0dao.forumapiservice.utils.ApiUtils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApiController
@RequiredArgsConstructor
@RequestMapping()
public class AuthorizationController {

        private final AuthorizationsService authorizationsService;

        @PostMapping("${end.points.login}")
        public ResponseEntity<ApiResponse<UserProfileDTO>> authorizeUser(@Valid @RequestBody LoginRequest authRequest) {
                log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

                AuthResult authResult = this.authorizationsService.loginUser(authRequest.getEmail(),
                                authRequest.getPassword());

                ApiResponse<UserProfileDTO> body = ApiResponse.tokenCreateUpdated(authResult.getUserProfile());
                return ResponseEntity.ok()
                                .header(HttpHeaders.SET_COOKIE,
                                                ApiUtils.createCookie(SecurityConstans.JWT_COOKIE_NAME,
                                                                authResult.getToken()))
                                .body(body);
        }

        @PostMapping("${end.points.register}")
        public ResponseEntity<Void> registerUser(@Valid @RequestBody RegisterRequest authRequest) {
                log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

                this.authorizationsService.registerUser(authRequest.getEmail(), authRequest.getPassword(),
                                authRequest.getConfirmPassword());
                return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        @GetMapping("${end.points.token}${end.points.refresh}")
        public ResponseEntity<Void> getRefreshToken() {
                String token = this.authorizationsService.refreshToken();
                return ResponseEntity.ok()
                                .header(HttpHeaders.SET_COOKIE,
                                                ApiUtils.createCookie(SecurityConstans.JWT_COOKIE_NAME, token))
                                .build();
        }

        @PostMapping("${end.points.logout}")
        public ResponseEntity<Void> logout() {
                this.authorizationsService.logoutUser();
                return ResponseEntity.status(HttpStatus.OK)
                                .header(HttpHeaders.SET_COOKIE, ApiUtils.deleteCookie(SecurityConstans.JWT_COOKIE_NAME))
                                .build();
        }
}
