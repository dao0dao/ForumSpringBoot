package com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.comment;

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
public class CommentDTO implements Serializable {
    
    private LocalDateTime created;
    private String message;
    private String username;
}
