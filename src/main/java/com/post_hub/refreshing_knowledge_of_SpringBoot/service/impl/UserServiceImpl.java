package com.post_hub.refreshing_knowledge_of_SpringBoot.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.post_hub.refreshing_knowledge_of_SpringBoot.mapper.UserMapper;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.constans.ApiErrorMessage;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.user.UserDTO;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.entities.Role;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.entities.User;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.exception.DataExistException;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.exception.InvalidDataException;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.exception.NotFoundException;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.request.user.NewUserRequest;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.request.user.UpdateUserRequest;
import com.post_hub.refreshing_knowledge_of_SpringBoot.repositories.RoleRepository;
import com.post_hub.refreshing_knowledge_of_SpringBoot.repositories.UserRepository;
import com.post_hub.refreshing_knowledge_of_SpringBoot.service.UserService;
import com.post_hub.refreshing_knowledge_of_SpringBoot.utils.CurrentUser;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@Service
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
    public UserDTO getById(@NotNull Integer userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.USER_ERROR_BY_ID.getMessage(userId)));

        return UserMapper.toDTO(user);
    }

    @Override
    public UserDTO createUser(NewUserRequest newUserRequest) {
        var currentUser = CurrentUser.isExist();

        if (currentUser) {
            throw new DataExistException("You are already logged in");
        }

        if(!newUserRequest.getPassword().equals(newUserRequest.getConfirmPassword())) {
            throw new InvalidDataException("Passwords do not match");
        }

        if (!this.isPasswordValid(newUserRequest.getPassword())) {
            throw new InvalidDataException(
                    "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one digit");
        }

        User user = UserMapper.toEntity(newUserRequest);


        if (this.userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
            throw new DataExistException("User already exist");
        }

        Role role = this.roleRepository.findByName(newUserRequest.getUserRole().toUpperCase()).orElseThrow(
                () -> new NotFoundException(ApiErrorMessage.ROLE_ERROR.getMessage((newUserRequest.getUserRole()))));

        user.setRoles(List.of(role));
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        User savedUser = this.userRepository.save(user);
        return UserMapper.toDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(int userId, UpdateUserRequest newUserRequest) {
        var currentUserId = CurrentUser.getUserId();

        if (userId != currentUserId && !CurrentUser.isSuperAdmin()) {
            throw new NotFoundException("Permission denied");
        }

        if(!newUserRequest.getPassword().equals(newUserRequest.getConfirmPassword())){
            throw new InvalidDataException("Passwords do not match");
        }

        if(!this.isPasswordValid(newUserRequest.getPassword())){
            throw new InvalidDataException(
                    "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one digit");
        }

        var existedDifferentUser = this.userRepository.existsByUsernameOrEmailAndIdNot(newUserRequest.getUsername(),
                newUserRequest.getEmail(), currentUserId);

        if (existedDifferentUser) {
            throw new DataExistException("Username or email already exist");
        }

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.USER_ERROR_BY_ID.getMessage(userId)));

        user.setUsername(!newUserRequest.getUsername().isBlank() ? newUserRequest.getUsername() : user.getUsername());
        user.setEmail(!newUserRequest.getEmail().isBlank() ? newUserRequest.getEmail() : user.getEmail());
        user.setPassword(
                !newUserRequest.getPassword().isBlank() ? this.passwordEncoder.encode(newUserRequest.getPassword())
                        : user.getPassword());
        return UserMapper.toDTO(this.userRepository.save(user));
    }

}