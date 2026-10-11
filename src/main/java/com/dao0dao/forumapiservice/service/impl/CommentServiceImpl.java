package com.dao0dao.forumapiservice.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.dao0dao.forumapiservice.mapper.CommentMapper;
import com.dao0dao.forumapiservice.model.constans.ApiErrorMessage;

import com.dao0dao.forumapiservice.model.dto.comment.CommentDTO;
import com.dao0dao.forumapiservice.model.exception.NotFoundException;
import com.dao0dao.forumapiservice.repositories.CommentRepository;
import com.dao0dao.forumapiservice.repositories.PostRepository;
import com.dao0dao.forumapiservice.repositories.UserRepository;
import com.dao0dao.forumapiservice.security.policy.PostAccessPolicy;
import com.dao0dao.forumapiservice.service.CommentService;
import com.dao0dao.forumapiservice.utils.CurrentUser;

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
        var post = this.postRepository.findVisibleById(postId, PostAccessPolicy.canViewDeleted())
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.POST_ERROR_BY_ID.getMessage(postId)));

        var userId = CurrentUser.getUserId();
        var user = this.userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.USER_ERROR_BY_ID.getMessage(userId)));

        var comment = this.commentRepository.save(CommentMapper.toEntity(message, user, post));
        return CommentMapper.toDTO(comment);
    }

    @Override
    public void softDeleteComment(Integer commentId) {
        var comment = this.commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.COMMENT_ERROR_BY_ID.getMessage(commentId)));

        comment.setIsDeleted(true);
        this.commentRepository.save(comment);
        
    }

}
