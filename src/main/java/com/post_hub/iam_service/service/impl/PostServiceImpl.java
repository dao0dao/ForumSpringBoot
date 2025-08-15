package com.post_hub.iam_service.service.impl;

import org.springframework.stereotype.Service;

import com.post_hub.iam_service.model.constans.ApiErrorMessage;
import com.post_hub.iam_service.model.dto.post.PostDTO;
import com.post_hub.iam_service.model.enteties.Post;
import com.post_hub.iam_service.model.exception.NotFoundException;
import com.post_hub.iam_service.model.request.PostRequest;
import com.post_hub.iam_service.model.response.ApiResponse;
import com.post_hub.iam_service.repositories.PostRepository;
import com.post_hub.iam_service.service.PostService;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    final private PostRepository postRepository;

    @Override
    public ApiResponse<PostDTO> getById(@NotNull Integer id) {
        Post post = this.postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.POST_ERROR_BY_ID.getMessage(id)));

        PostDTO postDTO = PostDTO.builder()
                .content(post.getContent())
                .created(post.getCreated())
                .id(post.getId())
                .likes(post.getLikes())
                .title(post.getTitle())
                .build();

        return ApiResponse.createSuccessful(postDTO);
    }

    @Override
    public ApiResponse<PostDTO> createPost(@NotNull PostRequest request) {
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
        Post savedPost = this.postRepository.save(post);
        PostDTO postDTO = PostDTO.builder()
                .content(savedPost.getContent())
                .created(savedPost.getCreated())
                .id(savedPost.getId())
                .likes(savedPost.getLikes())
                .title(savedPost.getTitle())
                .build();

        return ApiResponse.createSuccessful(postDTO);
    }

}
