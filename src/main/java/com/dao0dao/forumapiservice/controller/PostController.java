package com.dao0dao.forumapiservice.controller;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dao0dao.forumapiservice.model.constans.ApiLogMessage;
import com.dao0dao.forumapiservice.model.dto.post.PostDTO;
import com.dao0dao.forumapiservice.model.dto.post.PostSearchDTO;
import com.dao0dao.forumapiservice.model.entities.PostEntity_;
import com.dao0dao.forumapiservice.model.request.post.PostRequest;
import com.dao0dao.forumapiservice.model.request.post.PostSearchFilter;
import com.dao0dao.forumapiservice.model.response.ApiResponse;
import com.dao0dao.forumapiservice.model.response.payloads.PaginationPayload;
import com.dao0dao.forumapiservice.security.annotation.ActiveUser;
import com.dao0dao.forumapiservice.security.annotation.ApiController;
import com.dao0dao.forumapiservice.service.PostService;
import com.dao0dao.forumapiservice.utils.ApiUtils;
import com.dao0dao.forumapiservice.utils.CurrentUser;
import com.dao0dao.forumapiservice.utils.PageBuilder;

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
@ActiveUser
@ApiController
@RequiredArgsConstructor
@RequestMapping("${end.points.posts}")
public class PostController {

    private static final Set<String> SORTABLE_FIELDS = Set.of(
            PostEntity_.ID, PostEntity_.TITLE, PostEntity_.CREATED, PostEntity_.UPDATED);

    private final PostService postService;

    @GetMapping("${end.points.id}")
    public ResponseEntity<ApiResponse<PostDTO>> getPostById(@PathVariable(name = "id") Integer postId) {

        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        var postDTO = this.postService.getById(postId);
        ApiResponse<PostDTO> response = ApiResponse.createSuccessful(postDTO);
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

        var userId = CurrentUser.getUserId();

        var updatedPost = this.postService.updatePost(postId, post, userId);
        ApiResponse<PostDTO> response = ApiResponse.createSuccessful(updatedPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("${end.points.id}${end.points.like}")
    public ResponseEntity<ApiResponse<PostDTO>> likePost(@PathVariable(name = "id") Integer postId) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        var likedPost = this.postService.likePost(postId);
        ApiResponse<PostDTO> response = ApiResponse.createSuccessful(likedPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("${end.points.id}${end.points.dislike}")
    public ResponseEntity<ApiResponse<PostDTO>> dislikePost(@PathVariable(name = "id") Integer postId) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        var dislikedPost = this.postService.dislikePost(postId);
        ApiResponse<PostDTO> response = ApiResponse.createSuccessful(dislikedPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("${end.points.create}")
    public ResponseEntity<ApiResponse<PostDTO>> createPost(@RequestBody @Valid PostRequest request) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        Integer userId = CurrentUser.getUserId();
        var createdPost = this.postService.createPost(request, userId);
        ApiResponse<PostDTO> response = ApiResponse.createSuccessful(createdPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }    

    @GetMapping()
    public ResponseEntity<ApiResponse<PaginationPayload<PostSearchDTO>>> getAllPosts(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(required = false) ArrayList<String> sortsBy,
            @RequestParam(defaultValue = "false") Boolean authorName,
            @RequestParam(defaultValue = "false") Boolean title,
            @RequestParam(defaultValue = "false") Boolean content,
            @RequestParam(defaultValue = "") String keywords
        ) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        var pageable = PageBuilder.getPageable(page, limit, sortsBy, SORTABLE_FIELDS);
        var postSearchFilter = new PostSearchFilter(authorName, title, content, keywords);
        Page<PostSearchDTO> postsListPage = this.postService.searchPosts(postSearchFilter, pageable);

        PaginationPayload<PostSearchDTO> payload = new PaginationPayload<>(
                postsListPage.getContent(),
                new PaginationPayload.Pagination(
                        postsListPage.getTotalElements(),
                        pageable.getPageSize(),
                        postsListPage.getNumber(),
                        postsListPage.getTotalPages()));

        var response = ApiResponse.createSuccessful(payload);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
