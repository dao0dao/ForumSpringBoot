package com.dao0dao.forumapiservice.service.impl;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dao0dao.forumapiservice.mapper.UserMapper;
import com.dao0dao.forumapiservice.mapper.UserProfileMapper;
import com.dao0dao.forumapiservice.model.constans.ApiErrorMessage;
import com.dao0dao.forumapiservice.model.dto.user.UserDTO;
import com.dao0dao.forumapiservice.model.dto.user.UserProfileDTO;
import com.dao0dao.forumapiservice.model.entities.RoleEntity;
import com.dao0dao.forumapiservice.model.entities.UserEntity;
import com.dao0dao.forumapiservice.model.exception.DataExistException;
import com.dao0dao.forumapiservice.model.exception.InvalidDataException;
import com.dao0dao.forumapiservice.model.exception.NotFoundException;
import com.dao0dao.forumapiservice.model.request.user.NewUserRequest;
import com.dao0dao.forumapiservice.model.request.user.UpdateUserRequest;
import com.dao0dao.forumapiservice.repositories.RoleRepository;
import com.dao0dao.forumapiservice.repositories.UserRepository;
import com.dao0dao.forumapiservice.security.model.CustomUserDetails;
import com.dao0dao.forumapiservice.security.policy.UserAccessPolicy;
import com.dao0dao.forumapiservice.service.UserService;
import com.dao0dao.forumapiservice.utils.CurrentUser;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    final private UserRepository userRepository;
    final private PasswordEncoder passwordEncoder;
    final private RoleRepository roleRepository;

    private Boolean isPasswordValid(String password) {
        return password.length() >= 8 && password.matches(".*[A-Z].*") && password.matches(".*[a-z].*")
                && password.matches(".*\\d.*");
    }

    @Override
    public UserDTO getById(@NonNull Integer userId) {
        if (!UserAccessPolicy.hasAccess(userId)) {
            throw new NotFoundException("Permission denied");
        }
        UserEntity user = this.userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.USER_ERROR_BY_ID.getMessage(userId)));

        return UserMapper.toDTO(user);
    }

    @Override
    public UserDTO createUser(@NonNull NewUserRequest newUserRequest) {
        var currentUser = CurrentUser.isExist();

        if (currentUser) {
            throw new DataExistException("You are already logged in");
        }

        if (!newUserRequest.getPassword().equals(newUserRequest.getConfirmPassword())) {
            throw new InvalidDataException("Passwords do not match");
        }

        if (!this.isPasswordValid(newUserRequest.getPassword())) {
            throw new InvalidDataException(
                    "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one digit");
        }

        UserEntity user = UserMapper.toEntity(newUserRequest);

        if (this.userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
            throw new DataExistException("User already exist");
        }

        RoleEntity role = this.roleRepository.findByName(newUserRequest.getUserRole().toUpperCase()).orElseThrow(
                () -> new NotFoundException(ApiErrorMessage.ROLE_ERROR.getMessage((newUserRequest.getUserRole()))));

        user.setRoles(List.of(role));
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        UserEntity savedUser = this.userRepository.save(user);
        return UserMapper.toDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(@NonNull Integer userId, @NonNull UpdateUserRequest newUserRequest) {
        if (!UserAccessPolicy.hasAccess(userId)) {
            throw new NotFoundException("Permission denied");
        }

        if (!newUserRequest.getPassword().equals(newUserRequest.getConfirmPassword())) {
            throw new InvalidDataException("Passwords do not match");
        }

        if (!this.isPasswordValid(newUserRequest.getPassword())) {
            throw new InvalidDataException(
                    "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one digit");
        }

        var currentUserId = CurrentUser.getUserId();

        var existedDifferentUser = this.userRepository.existsByUsernameOrEmailAndIdNot(newUserRequest.getUsername(),
                newUserRequest.getEmail(), currentUserId);

        if (existedDifferentUser) {
            throw new DataExistException("Username or email already exist");
        }

        UserEntity user = this.userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.USER_ERROR_BY_ID.getMessage(userId)));

        user.setUsername(!newUserRequest.getUsername().isBlank() ? newUserRequest.getUsername() : user.getUsername());
        user.setEmail(!newUserRequest.getEmail().isBlank() ? newUserRequest.getEmail() : user.getEmail());
        user.setPassword(
                !newUserRequest.getPassword().isBlank() ? this.passwordEncoder.encode(newUserRequest.getPassword())
                        : user.getPassword());
        return UserMapper.toDTO(this.userRepository.save(user));
    }

    @Override
    public UserProfileDTO getCurrentUser() {
        CustomUserDetails userDetails = CurrentUser.getUserDetails();
        return UserProfileMapper.toDto(userDetails);
    }

}