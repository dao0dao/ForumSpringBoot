package com.post_hub.iam_service.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.post_hub.iam_service.mapper.UserProfileMapper;
import com.post_hub.iam_service.model.dto.user.UserProfileDTO;
import com.post_hub.iam_service.model.entities.User;
import com.post_hub.iam_service.model.exception.NotFoundException;
import com.post_hub.iam_service.model.response.ApiResponse;
import com.post_hub.iam_service.repositories.UserRepository;
import com.post_hub.iam_service.security.JwtTokenProvider;
import com.post_hub.iam_service.service.AuthorizationsService;
import com.post_hub.iam_service.service.models.AuthResult;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AuthorizationServiceImpl implements AuthorizationsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public AuthResult loginUser(String email, String password) {
        User user = this.userRepository.findByEmailAndDeletedFalse(email)
                .orElseThrow(() -> new NotFoundException(ApiResponse.unauthorized().getMessage()));

        if (!this.passwordEncoder.matches(password, user.getPassword())) {
            throw new NotFoundException(ApiResponse.unauthorized().getMessage());
        }

        UserProfileDTO userProfileDTO = UserProfileMapper.toDto(user);
        String token = jwtTokenProvider.generateToken(user);

        return new AuthResult(userProfileDTO, token);

    }

}
