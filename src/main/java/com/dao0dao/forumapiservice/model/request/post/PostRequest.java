package com.dao0dao.forumapiservice.model.request.post;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest implements Serializable {
    
    @NotBlank(message = "Title can not be empty")
    @Size(max = 255, message = "Title can not be longer than 255 characters")
    private String title;

    @NotBlank(message = "Content can not be empty")
    @Size(max = 2000, message = "Content can not be longer than 2000 characters")
    private String content;
}
