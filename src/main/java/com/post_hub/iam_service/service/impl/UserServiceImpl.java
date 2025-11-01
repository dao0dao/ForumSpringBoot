package com.post_hub.iam_service.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.post_hub.iam_service.mapper.UserMapper;
import com.post_hub.iam_service.model.constans.ApiErrorMessage;
import com.post_hub.iam_service.model.dto.user.UserDTO;
import com.post_hub.iam_service.model.entities.Role;
import com.post_hub.iam_service.model.entities.User;
import com.post_hub.iam_service.model.exception.DataExistException;
import com.post_hub.iam_service.model.exception.NotFoundException;
import com.post_hub.iam_service.model.request.user.NewUserRequest;
import com.post_hub.iam_service.repositories.RoleRepository;
import com.post_hub.iam_service.repositories.UserRepository;
import com.post_hub.iam_service.service.UserService;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    final private UserRepository userRepository;
    final private PasswordEncoder passwordEncoder;
    final private RoleRepository roleRepository;

    @Override
    public UserDTO getById(@NotNull Integer userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.USER_ERROR_BY_ID.getMessage(userId)));

        return UserMapper.toDTO(user);
    }

    @Override
    public UserDTO createUser(NewUserRequest newUserRequest) {
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

}