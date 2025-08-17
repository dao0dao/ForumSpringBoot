package com.post_hub.iam_service.mapper;

import com.post_hub.iam_service.model.dto.post.PostDTO;
import com.post_hub.iam_service.model.enteties.Post;
import com.post_hub.iam_service.model.request.PostRequest;

public class PostMapper {
    private PostMapper() {
    }

    public static Post toEntity(PostRequest postRequest) {
        if (postRequest == null) {
            return null;
        }
        return Post.builder().title(postRequest.getTitle()).content(postRequest.getContent()).build();

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
}
