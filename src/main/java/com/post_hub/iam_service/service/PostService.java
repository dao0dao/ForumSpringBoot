package com.post_hub.iam_service.service;


import org.springframework.data.domain.Pageable;

import com.post_hub.iam_service.model.dto.post.PostDTO;
import com.post_hub.iam_service.model.dto.post.PostSearchDTO;
import com.post_hub.iam_service.model.request.PostRequest;
import com.post_hub.iam_service.model.request.PostSearchRequest;
import com.post_hub.iam_service.model.response.ApiResponse;
import com.post_hub.iam_service.model.response.payloads.PaginationPayload;

import jakarta.annotation.Nonnull;

public interface PostService {

    ApiResponse<PostDTO> getById(@Nonnull Integer id);
    ApiResponse<PostDTO> createPost(@Nonnull PostRequest post);
    ApiResponse<PostDTO> updatePost(@Nonnull Integer id, @Nonnull PostRequest post);
    ApiResponse<PostDTO> likePost(@Nonnull Integer id);
    ApiResponse<PostDTO> dislikePost(@Nonnull Integer id);
    void softDeletePost(@Nonnull Integer id);
    ApiResponse<PaginationPayload<PostSearchDTO>> findAllPosts(Pageable pageable);
    ApiResponse<PaginationPayload<PostSearchDTO>> searchPosts(@Nonnull PostSearchRequest request, Pageable pageable);
}