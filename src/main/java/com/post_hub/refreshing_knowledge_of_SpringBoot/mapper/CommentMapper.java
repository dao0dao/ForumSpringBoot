package com.post_hub.refreshing_knowledge_of_SpringBoot.mapper;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.comment.CommentDTO;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.entities.CommentEntity;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.entities.PostEntity;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.entities.UserEntity;

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
