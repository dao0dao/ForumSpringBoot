package com.dao0dao.forumapiservice.mapper;

import java.util.List;

import com.dao0dao.forumapiservice.model.dto.comment.CommentDTO;
import com.dao0dao.forumapiservice.model.dto.post.PostDTO;
import com.dao0dao.forumapiservice.model.dto.post.PostSearchDTO;
import com.dao0dao.forumapiservice.model.entities.PostEntity;
import com.dao0dao.forumapiservice.model.entities.UserEntity;
import com.dao0dao.forumapiservice.model.request.post.PostRequest;

public class PostMapper {

    public static PostEntity toEntity(PostRequest postRequest, UserEntity author) {
        if (postRequest == null) {
            return null;
        }
        return PostEntity.builder().title(postRequest.getTitle()).content(postRequest.getContent()).author(author).build();

    }

    public static PostDTO toDTO(PostEntity post) {
        if (post == null) {
            return null;
        }

        List<CommentDTO> comments = post.getComments().stream().map(CommentMapper::toDTO).toList();

        return PostDTO.builder()
                .content(post.getContent())
                .created(post.getCreated())
                .id(post.getId())
                .likes(post.getLikesCount())
                .title(post.getTitle())
                .updated(post.getUpdated())
                .isDeleted(post.isDeleted())
                .version(post.getVersion())
                .comments(comments)
                .build();
    }

    public static PostSearchDTO toSearchDTO(PostEntity post, Integer currentUserId) {
        if (post == null) {
            return null;
        }

        return PostSearchDTO.builder()
                .authorName(post.getAuthor().getUsername())
                .canEdit(post.getAuthor().getId().equals(currentUserId))
                .content(post.getContent())
                .created(post.getCreated())
                .id(post.getId())
                .likes(post.getLikesCount())
                .commentsCount(post.getCommentsCount())
                .title(post.getTitle())
                .updated(post.getUpdated())
                .isDeleted(post.isDeleted())
                .version(post.getVersion())
                .build();
    }
}
