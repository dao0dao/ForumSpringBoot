package com.post_hub.iam_service.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.post_hub.iam_service.service.CommentService;

@Service
public class CommentServiceImp implements CommentService {

    private final List<String> comments = new ArrayList<>();

    @Override
    public void createComment(String comment) {
        this.comments.add(comment);
    }

}
