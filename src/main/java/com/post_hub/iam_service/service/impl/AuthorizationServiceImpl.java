package com.post_hub.iam_service.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.post_hub.iam_service.mapper.UserProfileMapper;
import com.post_hub.iam_service.model.dto.user.UserProfileDTO;
import com.post_hub.iam_service.security.JwtTokenProvider;
import com.post_hub.iam_service.security.model.CustomUserDetails;
import com.post_hub.iam_service.service.AuthorizationsService;
import com.post_hub.iam_service.service.models.AuthResult;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AuthorizationServiceImpl implements AuthorizationsService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public AuthResult loginUser(String email, String password) {

        var authToken = new UsernamePasswordAuthenticationToken(email, password);
        var authentication = authenticationManager.authenticate(authToken);

        var userDetails = (CustomUserDetails) authentication.getPrincipal();

        UserProfileDTO userProfileDTO = UserProfileMapper.toDto(userDetails);
        String token = jwtTokenProvider.generateToken(userDetails);

        return new AuthResult(userProfileDTO, token);

    }

}
