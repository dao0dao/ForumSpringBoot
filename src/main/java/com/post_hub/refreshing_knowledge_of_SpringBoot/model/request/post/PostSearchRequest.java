package com.post_hub.refreshing_knowledge_of_SpringBoot.model.request.post;

import java.io.Serializable;

import lombok.Data;

@Data
public class PostSearchRequest implements Serializable {
    private String title;
    private String content;
    private Integer likes;
    private Boolean deleted;
    private String keywords;
}
