package com.post_hub.refreshing_knowledge_of_SpringBoot.mapper;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.comment.CommentDTO;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.entities.CommentEntity;

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
}
