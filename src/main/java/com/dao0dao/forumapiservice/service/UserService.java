package com.dao0dao.forumapiservice.service;

import org.springframework.lang.NonNull;

import com.dao0dao.forumapiservice.model.dto.user.UserDTO;
import com.dao0dao.forumapiservice.model.dto.user.UserProfileDTO;
import com.dao0dao.forumapiservice.model.request.user.NewUserRequest;
import com.dao0dao.forumapiservice.model.request.user.UpdateUserRequest;

public interface UserService {
    UserDTO getById(@NonNull Integer userId);

    UserDTO createUser(@NonNull NewUserRequest newUserRequest);

    UserDTO updateUser(@NonNull Integer userId, @NonNull UpdateUserRequest newUserRequest);

    UserProfileDTO getCurrentUser();

}
