package com.post_hub.refreshing_knowledge_of_SpringBoot.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.comment.CommentDTO;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.request.comment.CommentRequest;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.response.ApiResponse;
import com.post_hub.refreshing_knowledge_of_SpringBoot.security.annotation.ActiveUser;
import com.post_hub.refreshing_knowledge_of_SpringBoot.security.annotation.ApiController;
import com.post_hub.refreshing_knowledge_of_SpringBoot.service.CommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@ActiveUser
@ApiController
@RequiredArgsConstructor
@RequestMapping("${end.points.posts}${end.points.id}")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("${end.points.comment}")
    public ResponseEntity<ApiResponse<CommentDTO>> createComment(
            @PathVariable(name = "id") Integer postId,
            @Valid @RequestBody CommentRequest commentRequest) {

        log.trace("Creating comment for post with ID: {}", postId);

        var createdComment = this.commentService.createComment(postId, commentRequest.getMessage());
        var response = ApiResponse.createSuccessful(createdComment);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("${end.points.comment}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable(name = "id") Integer commentId) {

        log.trace("Deleting comment with ID: {}", commentId);
        this.commentService.softDeleteComment(commentId);
        
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
