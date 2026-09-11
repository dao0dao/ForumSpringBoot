package com.dao0dao.forumapiservice.mapper;

import com.dao0dao.forumapiservice.model.dto.comment.CommentDTO;
import com.dao0dao.forumapiservice.model.entities.CommentEntity;
import com.dao0dao.forumapiservice.model.entities.PostEntity;
import com.dao0dao.forumapiservice.model.entities.UserEntity;

public class CommentMapper {
    public static CommentDTO toDTO(CommentEntity comment) {
        if (comment == null) {
            return null;
        }
        return CommentDTO.builder()
                .created(comment.getCreated())
                .message(comment.getMessage())
                .username(comment.getUser().getUsername())
                .build();
    }

    public static CommentEntity toEntity(String message, UserEntity user, PostEntity post) {
        if (message == null) {
            return null;
        }
        return CommentEntity.builder()
                .message(message)
                .user(user)
                .post(post)
                .build();
    }
}
