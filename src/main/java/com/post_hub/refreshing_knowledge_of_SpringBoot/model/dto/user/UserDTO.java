package com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.user;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.enums.RegistrationStatus;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {
    
    private Integer id;
    private String username;
    private LocalDateTime created;
    private LocalDateTime updated;
    private RegistrationStatus registration_status;
    private List<UserRole> roles;
    private LocalDateTime lastLogin;
    private Boolean deleted;
}
