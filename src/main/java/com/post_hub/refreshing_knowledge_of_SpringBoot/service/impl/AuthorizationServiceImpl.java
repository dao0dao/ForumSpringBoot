package com.post_hub.refreshing_knowledge_of_SpringBoot.service.impl;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.post_hub.refreshing_knowledge_of_SpringBoot.mapper.UserMapper;
import com.post_hub.refreshing_knowledge_of_SpringBoot.mapper.UserProfileMapper;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.constans.ApiConstans;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.constans.ApiErrorMessage;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.user.UserProfileDTO;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.entities.Role;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.entities.User;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.enums.UserRole;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.exception.DataExistException;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.exception.NotFoundException;
import com.post_hub.refreshing_knowledge_of_SpringBoot.repositories.RoleRepository;
import com.post_hub.refreshing_knowledge_of_SpringBoot.repositories.UserRepository;
import com.post_hub.refreshing_knowledge_of_SpringBoot.security.JwtTokenProvider;
import com.post_hub.refreshing_knowledge_of_SpringBoot.security.model.CustomUserDetails;
import com.post_hub.refreshing_knowledge_of_SpringBoot.service.AuthorizationsService;
import com.post_hub.refreshing_knowledge_of_SpringBoot.service.models.AuthResult;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AuthorizationServiceImpl implements AuthorizationsService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

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
    public Boolean registerUser(String email, String password, String confirmPassword) {
        if (password.length() < ApiConstans.PASSWORD_MIN_LENGTH ||
                password.length() > ApiConstans.PASSWORD_MAX_LENGTH ||
                !password.chars().anyMatch(singleChar -> ApiConstans.PASSWORD_SPECIAL_CHARS.indexOf(singleChar) >= 0) ||
                !password.chars().anyMatch(singleChar -> Character.isLowerCase(singleChar)) ||
                !password.chars().anyMatch(singleChar -> Character.isUpperCase(singleChar)) ) {
            throw new NotFoundException(
                    "Password must be between " + ApiConstans.PASSWORD_MIN_LENGTH + " and "
                            + ApiConstans.PASSWORD_MAX_LENGTH +
                            ", contain at least one special character: " + ApiConstans.PASSWORD_SPECIAL_CHARS +
                            ", one lowercase and one uppercase letter.");
        }
        if (!password.equals(confirmPassword)) {
            throw new NotFoundException("Password and Confirm password do not match.");
        }

        var isUserExist = this.userRepository.existsByEmail(email);
        if (isUserExist) {
            throw new DataExistException("can not create user");
        }
        password = this.passwordEncoder.encode(password);
        User user = UserMapper.toEntity(email, password);

        Role role = this.roleRepository.findByName(UserRole.USER.getRole()).orElseThrow(
                () -> new NotFoundException(ApiErrorMessage.ROLE_ERROR.getMessage((UserRole.USER.getRole()))));

        user.setRoles(List.of(role));

        this.userRepository.save(user);
        return true;
    }

    @Override
    public String refreshToken() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return jwtTokenProvider.generateToken(userDetails);
    }

}
