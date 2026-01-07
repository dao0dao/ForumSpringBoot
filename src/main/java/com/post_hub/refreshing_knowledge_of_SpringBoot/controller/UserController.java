package com.post_hub.refreshing_knowledge_of_SpringBoot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.constans.ApiLogMessage;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.user.UserDTO;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.request.user.NewUserRequest;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.response.ApiResponse;
import com.post_hub.refreshing_knowledge_of_SpringBoot.service.UserService;
import com.post_hub.refreshing_knowledge_of_SpringBoot.utils.ApiUtils;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RestController
@RequestMapping("${end.points.users}")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("${end.points.id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUser(@PathVariable(name = "id") Integer userId) {

        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        UserDTO userDTO = this.userService.getById(userId);
        ApiResponse<UserDTO> apiResponse = ApiResponse.createSuccessful(userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("${end.points.create}")
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@RequestBody @Valid NewUserRequest request) {

        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        UserDTO userDTO = this.userService.createUser(request);
        ApiResponse<UserDTO> apiResponse = ApiResponse.createSuccessful(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

}
