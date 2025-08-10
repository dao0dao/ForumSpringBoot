package com.post_hub.iam_service.service;


import com.post_hub.iam_service.model.dto.post.PostDTO;
import com.post_hub.iam_service.model.response.ApiResponse;

import jakarta.annotation.Nonnull;

public interface PostService {

    ApiResponse<PostDTO> getById(@Nonnull Integer id);
}