package com.post_hub.refreshing_knowledge_of_SpringBoot.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.post_hub.refreshing_knowledge_of_SpringBoot.mapper.PostMapper;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.constans.ApiErrorMessage;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.post.PostDTO;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.post.PostSearchDTO;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.entities.Post;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.entities.User;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.exception.DataExistException;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.exception.NoAccessException;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.exception.NotFoundException;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.request.post.PostRequest;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.request.post.PostSearchRequest;
import com.post_hub.refreshing_knowledge_of_SpringBoot.repositories.PostRepository;
import com.post_hub.refreshing_knowledge_of_SpringBoot.repositories.UserRepository;
import com.post_hub.refreshing_knowledge_of_SpringBoot.repositories.criteria.PostSearchCriteria;
import com.post_hub.refreshing_knowledge_of_SpringBoot.service.PostService;
import com.post_hub.refreshing_knowledge_of_SpringBoot.utils.CurrentUser;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    final private PostRepository postRepository;
    final private UserRepository userRepository;

    @Override
    public PostDTO getById(@NotNull Integer id) {
        Post post = this.postRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.POST_ERROR_BY_ID.getMessage(id)));

        return PostMapper.toDTO(post);
    }

    @Override
    public PostDTO createPost(@NotNull PostRequest request, @NotNull Integer userId) {
        if (this.postRepository.existsByTitle(request.getTitle())) {
            throw new DataExistException(ApiErrorMessage.POST_ALREADY_EXIST.getMessage(request.getTitle()));
        }
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(ApiErrorMessage.USER_ERROR_BY_ID.getMessage(userId)));

        Post post = PostMapper.toEntity(request, user);

        Post savedPost = this.postRepository.save(post);
        return PostMapper.toDTO(savedPost);
    }

    @Override
    public PostDTO updatePost(@NotNull Integer id, PostRequest request, @NotNull Integer userId) {
        Post updatedPost = this.postRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.POST_ERROR_BY_ID.getMessage(id)));

        if (!updatedPost.getUser().getId().equals(userId)) {
            throw new NoAccessException(ApiErrorMessage.USER_ACCESS_ERROR.getMessage(userId));
        }

        updatedPost.setTitle(request.getTitle());
        updatedPost.setContent(request.getContent());

        this.postRepository.save(updatedPost);
        return PostMapper.toDTO(updatedPost);
    }

    @Override
    @Transactional
    public PostDTO likePost(@NotNull Integer id) {
        var userId = CurrentUser.getUserId();
        this.postRepository.addLIke(userId, id);
        var post = this.postRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.POST_ERROR_BY_ID.getMessage(id)));

        return PostMapper.toDTO(post);
    }

    @Override
    public PostDTO dislikePost(@NotNull Integer id) {
        var userId = CurrentUser.getUserId();
        this.postRepository.deleteLike(userId, id);
        var post = this.postRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.POST_ERROR_BY_ID.getMessage(id)));

        return PostMapper.toDTO(post);
    }

    @Override
    public void softDeletePost(Integer id) {
        var post = this.postRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.POST_ERROR_BY_ID.getMessage(id)));
        post.setDeleted(true);
        this.postRepository.save(post);
    }

    @Override
    public Page<PostSearchDTO> findAllPosts(@NonNull Pageable pageable) {
        return this.postRepository.findAll(pageable).map(PostMapper::toSearchDTO);
    }

    @Override
    public Page<PostSearchDTO> searchPosts(PostSearchRequest request, @NonNull Pageable pageable) {
        Specification<Post> specification = new PostSearchCriteria(request);
        return this.postRepository.findAll(specification, pageable).map(PostMapper::toSearchDTO);
    }

}
