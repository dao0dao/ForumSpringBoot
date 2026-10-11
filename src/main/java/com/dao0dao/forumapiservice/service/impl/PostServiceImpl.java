package com.dao0dao.forumapiservice.service.impl;

import java.util.Objects;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.dao0dao.forumapiservice.mapper.PostMapper;
import com.dao0dao.forumapiservice.model.constans.ApiErrorMessage;
import com.dao0dao.forumapiservice.model.dto.post.PostDTO;
import com.dao0dao.forumapiservice.model.dto.post.PostSearchDTO;
import com.dao0dao.forumapiservice.model.entities.PostEntity;
import com.dao0dao.forumapiservice.model.entities.UserEntity;
import com.dao0dao.forumapiservice.model.exception.NotFoundException;
import com.dao0dao.forumapiservice.model.request.post.PostRequest;
import com.dao0dao.forumapiservice.model.request.post.PostSearchFilter;
import com.dao0dao.forumapiservice.model.request.post.UpdatePostRequest;
import com.dao0dao.forumapiservice.repositories.PostRepository;
import com.dao0dao.forumapiservice.repositories.UserRepository;
import com.dao0dao.forumapiservice.repositories.criteria.PostSearchCriteria;
import com.dao0dao.forumapiservice.security.policy.PostAccessPolicy;
import com.dao0dao.forumapiservice.service.PostService;
import com.dao0dao.forumapiservice.utils.CurrentUser;

import lombok.AllArgsConstructor;

@Service
@Validated
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    final private PostRepository postRepository;
    final private UserRepository userRepository;

    @Override
    public PostDTO getById(Integer id) {
        PostEntity post = this.postRepository.findVisibleById(id, PostAccessPolicy.canViewDeleted())
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.POST_ERROR_BY_ID.getMessage(id)));

        return PostMapper.toDTO(post);
    }

    @Override
    public PostDTO createPost(PostRequest request, Integer userId) {
        UserEntity user = this.userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(ApiErrorMessage.USER_ERROR_BY_ID.getMessage(userId)));

        PostEntity post = PostMapper.toEntity(request, user);

        @SuppressWarnings("null")
        PostEntity savedPost = this.postRepository.save(post);
        return PostMapper.toDTO(savedPost);
    }

    @Override
    public PostDTO updatePost(Integer id, UpdatePostRequest request, Integer userId) {
        PostEntity postToUpdate = this.postRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.POST_ERROR_BY_ID.getMessage(id)));

        PostAccessPolicy.verifyPostAccess(postToUpdate);

        if (!Objects.equals(postToUpdate.getVersion(), request.getVersion())) {
            throw new OptimisticLockingFailureException(ApiErrorMessage.RESOURCE_MODIFIED.getMessage());
        }

        postToUpdate.update(request.getTitle(), request.getContent());

        PostEntity savedPost = this.postRepository.save(postToUpdate);
        return PostMapper.toDTO(savedPost);
    }

    @Override
    @Transactional
    public PostDTO likePost(Integer id) {
        var userId = CurrentUser.getUserId();
        this.postRepository.addLIke(userId, id);
        var post = this.postRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.POST_ERROR_BY_ID.getMessage(id)));

        return PostMapper.toDTO(post);
    }

    @Override
    public PostDTO dislikePost(Integer id) {
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

        PostAccessPolicy.verifyPostAccess(post);

        post.markAsDeleted();
        this.postRepository.save(post);
    }

    @Override
    public Page<PostSearchDTO> searchPosts(PostSearchFilter request, Pageable pageable) {
        Specification<PostEntity> specification = new PostSearchCriteria(request, PostAccessPolicy.canViewDeleted());
        var currentUserId = CurrentUser.getUserId();
        return this.postRepository.findAll(specification, pageable).map(post -> PostMapper.toSearchDTO(post, currentUserId));
    }

}
