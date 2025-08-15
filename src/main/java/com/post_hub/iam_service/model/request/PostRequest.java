package com.post_hub.iam_service.model.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest implements Serializable {
    
    private String title;
    private String content;
}
