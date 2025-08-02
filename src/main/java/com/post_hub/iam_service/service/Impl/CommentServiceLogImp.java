package com.post_hub.iam_service.service.Impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.post_hub.iam_service.service.CommentService;

@Service("CommentServiceLog")
public class CommentServiceLogImp implements CommentService {

    private final List<String> comments = new ArrayList<>();

    @Override
    public void createComment(String comment) {
        String log = String.format("[%s] create comment %s", LocalDateTime.now(), comment);
        this.comments.add(comment);
        System.out.println(log);
    }

}
