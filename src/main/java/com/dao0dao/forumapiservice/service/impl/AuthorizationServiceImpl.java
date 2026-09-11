package com.dao0dao.forumapiservice.service.impl;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dao0dao.forumapiservice.mapper.UserMapper;
import com.dao0dao.forumapiservice.mapper.UserProfileMapper;
import com.dao0dao.forumapiservice.model.constans.ApiConstans;
import com.dao0dao.forumapiservice.model.constans.ApiErrorMessage;
import com.dao0dao.forumapiservice.model.dto.user.UserProfileDTO;
import com.dao0dao.forumapiservice.model.entities.RoleEntity;
import com.dao0dao.forumapiservice.model.entities.UserEntity;
import com.dao0dao.forumapiservice.model.enums.UserRole;
import com.dao0dao.forumapiservice.model.exception.DataExistException;
import com.dao0dao.forumapiservice.model.exception.NotFoundException;
import com.dao0dao.forumapiservice.repositories.RoleRepository;
import com.dao0dao.forumapiservice.repositories.UserRepository;
import com.dao0dao.forumapiservice.security.JwtTokenProvider;
import com.dao0dao.forumapiservice.security.model.CustomUserDetails;
import com.dao0dao.forumapiservice.security.validators.PasswordValidator;
import com.dao0dao.forumapiservice.service.AuthorizationsService;
import com.dao0dao.forumapiservice.service.models.AuthResult;
import com.dao0dao.forumapiservice.utils.CurrentUser;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AuthorizationServiceImpl implements AuthorizationsService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @SuppressWarnings("null")
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
        if (!PasswordValidator.isSamePasswords(password, confirmPassword)) {
            throw new NotFoundException("Password and Confirm password do not match.");
        }
        
        if (!PasswordValidator.isValid(password)) {
            throw new NotFoundException(
                    "Password must be between " + ApiConstans.PASSWORD_MIN_LENGTH + " and "
                            + ApiConstans.PASSWORD_MAX_LENGTH +
                            ", contain at least one special character: " + ApiConstans.PASSWORD_SPECIAL_CHARS +
                            ", one lowercase and one uppercase letter.");
        }

        if (this.userRepository.existsByEmail(email)) {
            throw new DataExistException("can not create user");
        }

        password = this.passwordEncoder.encode(password);
        UserEntity user = UserMapper.toEntity(email, password);

        RoleEntity role = this.roleRepository.findByName(UserRole.USER.getRole()).orElseThrow(
                () -> new NotFoundException(ApiErrorMessage.ROLE_ERROR.getMessage((UserRole.USER.getRole()))));

        user.setRoles(List.of(role));

        this.userRepository.save(user);
        return true;
    }

    @SuppressWarnings("null")
    @Override
    public String refreshToken() {
        CustomUserDetails userDetails = CurrentUser.getUserDetails();
        return jwtTokenProvider.generateToken(userDetails);
    }

    @Override
    public void logoutUser() {
        SecurityContextHolder.clearContext();
    }
}
