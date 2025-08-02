package com.post_hub.iam_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PostServiceImp implements PostService {

    private final List<String> posts = new ArrayList<>();

    @Override
    public void createPost(String postContent) {
        this.posts.add(postContent);
    }

}
