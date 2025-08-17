package com.post_hub.iam_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.post_hub.iam_service.model.constans.ApiLogMessage;
import com.post_hub.iam_service.model.dto.post.PostDTO;
import com.post_hub.iam_service.model.request.PostRequest;
import com.post_hub.iam_service.model.response.ApiResponse;
import com.post_hub.iam_service.service.PostService;
import com.post_hub.iam_service.utils.ApiUtils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("${end.points.posts}")
public class PostController {

    private final PostService postService;

    @GetMapping("${end.points.id}")
    public ResponseEntity<ApiResponse<PostDTO>> getPostById(@PathVariable(name = "id") Integer postId) {

        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        ApiResponse<PostDTO> response = this.postService.getById(postId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("${end.points.id}${end.points.update}")
    public ResponseEntity<ApiResponse<PostDTO>> updatePostById(@PathVariable(name = "id") Integer postId,
            @Valid @RequestBody PostRequest post) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        ApiResponse<PostDTO> response = this.postService.updatePost(postId, post);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("${end.points.id}${end.points.like}")
    public ResponseEntity<ApiResponse<PostDTO>> likePost(@PathVariable(name = "id") Integer postId) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        ApiResponse<PostDTO> response = this.postService.likePost(postId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("${end.points.id}${end.points.dislike}")
    public ResponseEntity<ApiResponse<PostDTO>> dislikePost(@PathVariable(name = "id") Integer postId) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        ApiResponse<PostDTO> response = this.postService.dislikePost(postId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("${end.points.create}")
    public ResponseEntity<ApiResponse<PostDTO>> createPost(@RequestBody @Valid PostRequest request) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        ApiResponse<PostDTO> response = this.postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
