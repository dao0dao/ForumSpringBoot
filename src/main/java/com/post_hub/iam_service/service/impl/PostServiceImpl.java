package com.post_hub.iam_service.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.post_hub.iam_service.mapper.PostMapper;
import com.post_hub.iam_service.model.constans.ApiErrorMessage;
import com.post_hub.iam_service.model.dto.post.PostDTO;
import com.post_hub.iam_service.model.enteties.Post;
import com.post_hub.iam_service.model.exception.DataExistException;
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

        PostDTO postDTO = PostMapper.toDTO(post);

        return ApiResponse.createSuccessful(postDTO);
    }

    @Override
    public ApiResponse<PostDTO> createPost(@NotNull PostRequest request) {
        if (this.postRepository.existsByTitle(request.getTitle())) {
            throw new DataExistException(ApiErrorMessage.POST_ALREADY_EXIST.getMessage(request.getTitle()));
        }
        Post post = PostMapper.toEntity(request);
        Post savedPost = this.postRepository.save(post);
        PostDTO postDTO = PostMapper.toDTO(savedPost);

        return ApiResponse.createSuccessful(postDTO);
    }

    @Override
    public ApiResponse<PostDTO> updatePost(@NotNull Integer id, PostRequest request) {
        Post updatedPost = this.postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.POST_ERROR_BY_ID.getMessage(id)));

        updatedPost.setTitle(request.getTitle());
        updatedPost.setContent(request.getContent());
        updatedPost.setUpdated(LocalDateTime.now());
        
        this.postRepository.save(updatedPost);
        PostDTO postDTO = PostMapper.toDTO(updatedPost);

        return ApiResponse.createSuccessful(postDTO);
    }

    @Override
    public ApiResponse<PostDTO> likePost(@NotNull Integer id) {
        var post = this.postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.POST_ERROR_BY_ID.getMessage(id)));
        post.setLikes(post.getLikes() + 1);
        var savedPost = this.postRepository.save(post);
        PostDTO postDTO = PostMapper.toDTO(savedPost);

        return ApiResponse.createSuccessful(postDTO);
    }

    @Override
    public ApiResponse<PostDTO> dislikePost(@NotNull Integer id) {
        var post = this.postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.POST_ERROR_BY_ID.getMessage(id)));
        post.setLikes(post.getLikes() == 0 ? 0 : post.getLikes() - 1);
        var savedPost = this.postRepository.save(post);
        PostDTO postDTO = PostMapper.toDTO(savedPost);

        return ApiResponse.createSuccessful(postDTO);
    }

}
