package com.post_hub.iam_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.post_hub.iam_service.model.dto.CreateCommentRequest;
import com.post_hub.iam_service.service.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    private final CommentService commentServiceLog;

    @Autowired
    public CommentController(
        CommentService commentService,
      @Qualifier("CommentServiceLog") CommentService commentServiceLog
    ) {
        this.commentService = commentService;
        this.commentServiceLog = commentServiceLog;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createComment(@RequestBody CreateCommentRequest requestBody) {
        String comment = requestBody.getContent();
        this.commentService.createComment(comment);
        return new ResponseEntity<String>("Comment have been created", HttpStatus.OK);
    }

    @PostMapping("/create/log")
    public ResponseEntity<String> createCommentLog(@RequestBody CreateCommentRequest requestBody) {
        String comment = requestBody.getContent();
        this.commentServiceLog.createComment(comment);
        return new ResponseEntity<String>("Comment have been created", HttpStatus.OK);
    }
}
