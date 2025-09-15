package com.post_hub.iam_service.controller;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.post_hub.iam_service.model.constans.ApiLogMessage;
import com.post_hub.iam_service.model.dto.post.PostDTO;
import com.post_hub.iam_service.model.dto.post.PostSearchDTO;
import com.post_hub.iam_service.model.request.post.PostRequest;
import com.post_hub.iam_service.model.request.post.PostSearchRequest;
import com.post_hub.iam_service.model.response.ApiResponse;
import com.post_hub.iam_service.model.response.payloads.PaginationPayload;
import com.post_hub.iam_service.service.PostService;
import com.post_hub.iam_service.utils.ApiUtils;
import com.post_hub.iam_service.utils.PageBuilder;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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

    @DeleteMapping("${end.points.id}")
    public ResponseEntity<Void> deletePostById(@PathVariable(name = "id") Integer postId) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        this.postService.softDeletePost(postId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("${end.points.id}${end.points.update}")
    public ResponseEntity<ApiResponse<PostDTO>> updatePostById(@PathVariable(name = "id") Integer postId,
            @Valid @RequestBody PostRequest post) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        // TODO when will be user validation remove this mock
        Integer userId = 1;

        ApiResponse<PostDTO> response = this.postService.updatePost(postId, post, userId);
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

        // TODO when will be user validation remove this mock
        Integer userId = 1;

        ApiResponse<PostDTO> response = this.postService.createPost(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("${end.points.all}")
    public ResponseEntity<ApiResponse<PaginationPayload<PostSearchDTO>>> getAllPosts(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(required = false) ArrayList<String> sortsBy) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        var response = this.postService.findAllPosts(PageBuilder.getPageable(page, limit, sortsBy));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("${end.points.search}")
    public ResponseEntity<ApiResponse<PaginationPayload<PostSearchDTO>>> searchPost(
            @RequestBody @Valid PostSearchRequest request,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(required = false) ArrayList<String> sortsBy) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        var response = this.postService.searchPosts(request, PageBuilder.getPageable(page, limit, sortsBy));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
