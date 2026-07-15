package com.post_hub.refreshing_knowledge_of_SpringBoot.model.request.post;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostSearchFilter implements Serializable {
    private Boolean authorName;
    private Boolean title;
    private Boolean content;
    private String keywords;
}
