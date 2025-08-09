package com.post_hub.iam_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.post_hub.iam_service.model.enteties.Post;
import com.post_hub.iam_service.repositories.PostRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequiredArgsConstructor
@RequestMapping("${end.points.posts}")
public class PostController {

    private final PostRepository postRepository;

    @GetMapping("${end.points.id}")
    public ResponseEntity<Post> getPostById(
            @PathVariable(name = "id") Integer postId) {

        return this.postRepository.findById(postId).map(ResponseEntity::ok).orElseGet(() -> {
            return ResponseEntity.notFound().build();
        });
    }

}
