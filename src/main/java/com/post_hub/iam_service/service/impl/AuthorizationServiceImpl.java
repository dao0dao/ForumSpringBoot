package com.post_hub.iam_service.service.impl;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.post_hub.iam_service.mapper.UserMapper;
import com.post_hub.iam_service.mapper.UserProfileMapper;
import com.post_hub.iam_service.model.constans.ApiErrorMessage;
import com.post_hub.iam_service.model.dto.user.UserProfileDTO;
import com.post_hub.iam_service.model.entities.Role;
import com.post_hub.iam_service.model.entities.User;
import com.post_hub.iam_service.model.enums.UserRole;
import com.post_hub.iam_service.model.exception.DataExistException;
import com.post_hub.iam_service.model.exception.NotFoundException;
import com.post_hub.iam_service.repositories.RoleRepository;
import com.post_hub.iam_service.repositories.UserRepository;
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
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public AuthResult loginUser(String email, String password) {

        var authToken = new UsernamePasswordAuthenticationToken(email, password);
        var authentication = authenticationManager.authenticate(authToken);

        var userDetails = (CustomUserDetails) authentication.getPrincipal();

        UserProfileDTO userProfileDTO = UserProfileMapper.toDto(userDetails);
        String token = jwtTokenProvider.generateToken(userDetails);

        return new AuthResult(userProfileDTO, token);

    }

    @Override
    public Boolean registerUser(String email, String password) {
        var isUserExist = this.userRepository.existsByEmail(email);
        if (isUserExist) {
            throw new DataExistException("can not create user");
        }

        User user = UserMapper.toEntity(email, password);

        Role role = this.roleRepository.findByName(UserRole.USER.getRole()).orElseThrow(
                () -> new NotFoundException(ApiErrorMessage.ROLE_ERROR.getMessage((UserRole.USER.getRole()))));

        user.setRoles(List.of(role));

        this.userRepository.save(user);
        return true;
    }

    @Override
    public String refreshToken() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return jwtTokenProvider.generateToken(userDetails);
    }

}
