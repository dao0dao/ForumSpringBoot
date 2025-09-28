package com.post_hub.iam_service.model.dto.user;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import com.post_hub.iam_service.model.dto.role.RoleDTO;
import com.post_hub.iam_service.model.enums.RegistrationStatus;

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
    private Set<RoleDTO> roles;
    private LocalDateTime lastLogin;
    private Boolean deleted;
}
