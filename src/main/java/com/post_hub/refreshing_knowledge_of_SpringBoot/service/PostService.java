package com.post_hub.refreshing_knowledge_of_SpringBoot.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.post.PostDTO;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.post.PostSearchDTO;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.request.post.PostRequest;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.request.post.PostSearchRequest;

import jakarta.validation.constraints.NotNull;

public interface PostService {

    PostDTO getById(@NotNull Integer id);
    PostDTO createPost(@NotNull PostRequest post, @NotNull Integer userId);
    PostDTO updatePost(@NotNull Integer id, @NotNull PostRequest post, @NotNull Integer userId);
    PostDTO likePost(@NotNull Integer id);
    PostDTO dislikePost(@NotNull Integer id);
    void softDeletePost(@NotNull Integer id);
    Page<PostSearchDTO> findAllPosts(Pageable pageable);
    Page<PostSearchDTO> searchPosts(@NotNull PostSearchRequest request, Pageable pageable);
}