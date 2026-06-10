package com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.comment;

import java.time.LocalDateTime;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.user.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    
    private LocalDateTime created;
    private String message;
    private String username;
}
