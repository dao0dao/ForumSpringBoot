package com.dao0dao.forumapiservice.model.dto.post;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import com.dao0dao.forumapiservice.model.dto.comment.CommentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO implements Serializable {

    private Integer id;
    private String title;
    private String content;
    private Integer likes;
    private Instant created;
    private Instant updated;
    private Boolean isDeleted;
    private Long version;
    private List<CommentDTO> comments;
}
