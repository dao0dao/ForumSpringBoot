package com.post_hub.iam_service.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreatePostRequest {
    private String title;
    private String content;

}
