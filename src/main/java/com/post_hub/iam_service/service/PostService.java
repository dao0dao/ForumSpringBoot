package com.post_hub.iam_service.service;


import org.springframework.data.domain.Pageable;

import com.post_hub.iam_service.model.dto.post.PostDTO;
import com.post_hub.iam_service.model.dto.post.PostSearchDTO;
import com.post_hub.iam_service.model.request.post.PostRequest;
import com.post_hub.iam_service.model.request.post.PostSearchRequest;
import com.post_hub.iam_service.model.response.ApiResponse;
import com.post_hub.iam_service.model.response.payloads.PaginationPayload;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;

public interface PostService {

    ApiResponse<PostDTO> getById(@Nonnull Integer id);
    ApiResponse<PostDTO> createPost(@Nonnull PostRequest post, @Nonnull Integer userId);
    ApiResponse<PostDTO> updatePost(@Nonnull Integer id, @Nonnull PostRequest post, @Nonnull Integer userId);
    ApiResponse<PostDTO> likePost(@Nonnull Integer id);
    ApiResponse<PostDTO> dislikePost(@Nonnull Integer id);
    void softDeletePost(@Nonnull Integer id);
    ApiResponse<PaginationPayload<PostSearchDTO>> findAllPosts(Pageable pageable);
    ApiResponse<PaginationPayload<PostSearchDTO>> searchPosts(@Nonnull PostSearchRequest request, Pageable pageable);
}