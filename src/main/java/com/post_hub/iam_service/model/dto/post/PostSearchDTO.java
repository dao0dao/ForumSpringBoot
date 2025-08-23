package com.post_hub.iam_service.model.dto.post;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostSearchDTO implements Serializable {

    private Integer id;
    private String title;
    private String content;
    private Integer likes;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Boolean isDeleted;
}
