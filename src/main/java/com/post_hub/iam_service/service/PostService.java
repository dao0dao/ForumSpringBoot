package com.post_hub.iam_service.service;


import com.post_hub.iam_service.model.dto.post.PostDTO;
import com.post_hub.iam_service.model.request.PostRequest;
import com.post_hub.iam_service.model.response.ApiResponse;

import jakarta.annotation.Nonnull;

public interface PostService {

    ApiResponse<PostDTO> getById(@Nonnull Integer id);
    ApiResponse<PostDTO> createPost(@Nonnull PostRequest post);
    ApiResponse<PostDTO> updatePost(@Nonnull Integer id, @Nonnull PostRequest post);
    ApiResponse<PostDTO> likePost(@Nonnull Integer id);
    ApiResponse<PostDTO> dislikePost(@Nonnull Integer post);
}