package com.post_hub.refreshing_knowledge_of_SpringBoot.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.constans.ApiLogMessage;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.user.UserDTO;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.user.UserProfileDTO;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.request.user.NewUserRequest;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.request.user.UpdateUserRequest;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.response.ApiResponse;
import com.post_hub.refreshing_knowledge_of_SpringBoot.security.annotation.AccessLevel;
import com.post_hub.refreshing_knowledge_of_SpringBoot.security.annotation.ApiController;
import com.post_hub.refreshing_knowledge_of_SpringBoot.service.UserService;
import com.post_hub.refreshing_knowledge_of_SpringBoot.utils.ApiUtils;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@ApiController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("${end.points.users}${end.points.id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUser(@NonNull @PathVariable(name = "id") Integer userId) {

        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        UserDTO userDTO = this.userService.getById(userId);
        ApiResponse<UserDTO> apiResponse = ApiResponse.createSuccessful(userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @AccessLevel(requiredLevel = "SUPER_ADMIN")
    @PostMapping("${end.points.users}${end.points.create}")
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@NonNull @RequestBody @Valid NewUserRequest request) {

        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        UserDTO userDTO = this.userService.createUser(request);
        ApiResponse<UserDTO> apiResponse = ApiResponse.createSuccessful(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PatchMapping("${end.points.users}${end.points.id}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(@NonNull @PathVariable(name = "id") Integer userId,
            @NonNull @RequestBody @Valid UpdateUserRequest request) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        UserDTO userDTO = this.userService.updateUser(userId, request);
        ApiResponse<UserDTO> apiResponse = ApiResponse.createSuccessful(userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("${end.points.user}${end.points.status}")
    public ResponseEntity<ApiResponse<UserProfileDTO>> getUserStatus() {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        UserProfileDTO userProfileDTO = this.userService.getCurrentUser();
        ApiResponse<UserProfileDTO> response = ApiResponse.createSuccessful(userProfileDTO);
        return ResponseEntity.ok().body(response);
    }

}
