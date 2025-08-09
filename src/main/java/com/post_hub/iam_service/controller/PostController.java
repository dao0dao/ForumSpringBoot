package com.post_hub.iam_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.post_hub.iam_service.model.constans.ApiErrorMessage;
import com.post_hub.iam_service.model.constans.ApiLogMessage;
import com.post_hub.iam_service.model.enteties.Post;
import com.post_hub.iam_service.repositories.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("${end.points.posts}")
public class PostController {

    private final PostRepository postRepository;

    @GetMapping("${end.points.id}")
    public ResponseEntity<Post> getPostById(@PathVariable(name = "id") Integer postId) {

        log.info(ApiLogMessage.POST_INFO_BY_ID.getMessage(postId));

        return this.postRepository.findById(postId).map(ResponseEntity::ok).orElseGet(() -> {
            log.info(ApiErrorMessage.POST_ERROR_BY_ID.getMessage(postId));
            return ResponseEntity.notFound().build();
        });
    }

}
