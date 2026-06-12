package com.post_hub.refreshing_knowledge_of_SpringBoot.service;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.comment.CommentDTO;

import jakarta.validation.constraints.NotNull;

public interface CommentService {
    CommentDTO createComment(@NotNull Integer postId, @NotNull String message);
    void softDeleteComment(@NotNull Integer commentId);
}
