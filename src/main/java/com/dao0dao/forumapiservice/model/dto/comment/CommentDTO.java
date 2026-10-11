package com.dao0dao.forumapiservice.model.dto.comment;

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
public class CommentDTO implements Serializable {

    private Integer id;
    private Instant created;
    private Boolean isDeleted;
    private String message;
    private String username;
}
