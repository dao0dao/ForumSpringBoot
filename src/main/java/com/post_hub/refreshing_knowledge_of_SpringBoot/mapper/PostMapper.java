package com.post_hub.refreshing_knowledge_of_SpringBoot.mapper;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.post.PostDTO;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.post.PostSearchDTO;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.entities.Post;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.entities.User;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.request.post.PostRequest;

public class PostMapper {

    public static Post toEntity(PostRequest postRequest, User user) {
        if (postRequest == null) {
            return null;
        }
        return Post.builder().title(postRequest.getTitle()).content(postRequest.getContent()).user(user).build();

    }

    public static PostDTO toDTO(Post post) {
        if (post == null) {
            return null;
        }
        return PostDTO.builder()
                .content(post.getContent())
                .created(post.getCreated())
                .id(post.getId())
                .likes(post.getLikes())
                .title(post.getTitle())
                .updated(post.getUpdated())
                .build();
    }

    public static PostSearchDTO toSearchDTO(Post post) {
        if (post == null) {
            return null;
        }
        return PostSearchDTO.builder()
                .content(post.getContent())
                .created(post.getCreated())
                .id(post.getId())
                .likes(post.getLikes())
                .title(post.getTitle())
                .updated(post.getUpdated())
                .isDeleted(post.getDeleted())
                .build();
    }
}
