package com.post_hub.refreshing_knowledge_of_SpringBoot.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.post.PostDTO;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.post.PostSearchDTO;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.request.post.PostRequest;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.request.post.PostSearchRequest;

import jakarta.annotation.Nonnull;

public interface PostService {

    PostDTO getById(@Nonnull Integer id);
    PostDTO createPost(@Nonnull PostRequest post, @Nonnull Integer userId);
    PostDTO updatePost(@Nonnull Integer id, @Nonnull PostRequest post, @Nonnull Integer userId);
    PostDTO likePost(@Nonnull Integer id);
    PostDTO dislikePost(@Nonnull Integer id);
    void softDeletePost(@Nonnull Integer id);
    Page<PostSearchDTO> findAllPosts(Pageable pageable);
    Page<PostSearchDTO> searchPosts(@Nonnull PostSearchRequest request, Pageable pageable);
}