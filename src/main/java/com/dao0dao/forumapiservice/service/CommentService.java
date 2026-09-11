package com.dao0dao.forumapiservice.service;

import com.dao0dao.forumapiservice.model.dto.comment.CommentDTO;

import jakarta.validation.constraints.NotNull;

public interface CommentService {
    CommentDTO createComment(@NotNull Integer postId, @NotNull String message);
    void softDeleteComment(@NotNull Integer commentId);
}
