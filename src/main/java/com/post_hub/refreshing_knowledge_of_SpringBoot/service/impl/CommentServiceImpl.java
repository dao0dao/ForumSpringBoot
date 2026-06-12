package com.post_hub.refreshing_knowledge_of_SpringBoot.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.post_hub.refreshing_knowledge_of_SpringBoot.mapper.CommentMapper;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.constans.ApiErrorMessage;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.comment.CommentDTO;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.exception.NotFoundException;
import com.post_hub.refreshing_knowledge_of_SpringBoot.repositories.CommentRepository;
import com.post_hub.refreshing_knowledge_of_SpringBoot.repositories.PostRepository;
import com.post_hub.refreshing_knowledge_of_SpringBoot.repositories.UserRepository;
import com.post_hub.refreshing_knowledge_of_SpringBoot.service.CommentService;
import com.post_hub.refreshing_knowledge_of_SpringBoot.utils.CurrentUser;

import lombok.AllArgsConstructor;

@Service
@Validated
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    final private CommentRepository commentRepository;
    final private PostRepository postRepository;
    final private UserRepository userRepository;

    @Override
    public CommentDTO createComment(Integer postId, String message) {
        var post = this.postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.POST_ERROR_BY_ID.getMessage(postId)));

        var userId = CurrentUser.getUserId();
        var user = this.userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.USER_ERROR_BY_ID.getMessage(userId)));

        var comment = this.commentRepository.save(CommentMapper.toEntity(message, user, post));
        return CommentMapper.toDTO(comment);
    }

    @Override
    public void softDeleteComment(Integer commentId) {

    }

}
