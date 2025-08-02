package com.post_hub.iam_service.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.post_hub.iam_service.service.PostService;

@Service
public class PostServiceImpl implements PostService {

    private final List<String> posts = new ArrayList<>();

    @Override
    public void createPost(String postContent) {
        this.posts.add(postContent);
    }

}
