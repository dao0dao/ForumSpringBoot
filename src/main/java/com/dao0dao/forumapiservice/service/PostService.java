package com.dao0dao.forumapiservice.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dao0dao.forumapiservice.model.dto.post.PostDTO;
import com.dao0dao.forumapiservice.model.dto.post.PostSearchDTO;
import com.dao0dao.forumapiservice.model.request.post.PostRequest;
import com.dao0dao.forumapiservice.model.request.post.PostSearchFilter;
import com.dao0dao.forumapiservice.model.request.post.UpdatePostRequest;

import jakarta.validation.constraints.NotNull;

public interface PostService {

    PostDTO getById(@NotNull Integer id);
    PostDTO createPost(@NotNull PostRequest post, @NotNull Integer userId);
    PostDTO updatePost(@NotNull Integer id, @NotNull UpdatePostRequest post, @NotNull Integer userId);
    PostDTO likePost(@NotNull Integer id);
    PostDTO dislikePost(@NotNull Integer id);
    void softDeletePost(@NotNull Integer id);
    Page<PostSearchDTO> searchPosts(@NotNull PostSearchFilter request, Pageable pageable);
}