package com.post_hub.iam_service.model.request.post;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest implements Serializable {
    
    @NotBlank(message = "Title can not be empty")
    private String title;
    @NotBlank(message = "Content can not be empty")
    private String content;
}
