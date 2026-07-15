package com.post_hub.refreshing_knowledge_of_SpringBoot.mapper;

import java.util.List;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.comment.CommentDTO;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.post.PostDTO;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.post.PostSearchDTO;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.entities.PostEntity;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.entities.UserEntity;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.request.post.PostRequest;

public class PostMapper {

    public static PostEntity toEntity(PostRequest postRequest, UserEntity user) {
        if (postRequest == null) {
            return null;
        }
        return PostEntity.builder().title(postRequest.getTitle()).content(postRequest.getContent()).user(user).build();

    }

    public static PostDTO toDTO(PostEntity post) {
        if (post == null) {
            return null;
        }

        List<CommentDTO> comments = null;
        if (post.getComments() != null) {
            comments = post.getComments().stream().map(comment -> {
                var commentDTO = CommentMapper.toDTO(comment);
                if (commentDTO != null && comment.getIsDeleted()) {
                    commentDTO.setMessage("This comment has been deleted.");
                }
                return commentDTO;
            }).toList();

        }

        return PostDTO.builder()                
                .content(post.getContent())
                .created(post.getCreated())
                .id(post.getId())
                .likes(post.getLikesCount())
                .title(post.getTitle())
                .updated(post.getUpdated())
                .comments(comments)
                .build();
    }

    public static PostSearchDTO toSearchDTO(PostEntity post, Integer currentUserId) {
        if (post == null) {
            return null;
        }

        List<CommentDTO> comments = null;
        if (post.getComments() != null) {
            comments = post.getComments().stream().map(comment -> {
                var commentDTO = CommentMapper.toDTO(comment);
                if (commentDTO != null && comment.getIsDeleted()) {
                    commentDTO.setMessage("This comment has been deleted.");
                }
                return commentDTO;
            }).toList();

        }

        return PostSearchDTO.builder()
                .authorName(post.getUser().getUsername())
                .canEdit(post.getUser().getId().equals(currentUserId))
                .content(post.getContent())
                .created(post.getCreated())
                .id(post.getId())
                .likes(post.getLikesCount())
                .title(post.getTitle())
                .updated(post.getUpdated())
                .isDeleted(post.getDeleted())
                .comments(comments)
                .build();
    }
}
