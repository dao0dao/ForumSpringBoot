package com.post_hub.iam_service.controller;

import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.post_hub.iam_service.model.dto.CreatePostRequest;
import com.post_hub.iam_service.service.PostServiceImp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostServiceImp postService;

    @Autowired
    public PostController(PostServiceImp postService) {
        this.postService = postService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPost(@RequestBody CreatePostRequest requestBody) {
        String title = requestBody.getTitle();
        String content = requestBody.getContent();
        String post = "Title: " + title + "\nContent" + content;
        this.postService.createPost(post);
        return new ResponseEntity<String>("Created post with title: " + title, HttpStatus.OK);
    }

}
