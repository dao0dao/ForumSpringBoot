package com.dao0dao.forumapiservice.model.dto.post;

import java.io.Serializable;
import java.time.Instant;

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
    private String authorName;
    private String title;
    private String content;
    private Integer likes;
    private Integer commentsCount;
    private Instant created;
    private Instant updated;
    private Boolean isDeleted;
    private Boolean canEdit;
    private Long version;
}
