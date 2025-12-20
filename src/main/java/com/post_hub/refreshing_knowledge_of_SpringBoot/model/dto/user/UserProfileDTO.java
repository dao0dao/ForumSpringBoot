package com.post_hub.refreshing_knowledge_of_SpringBoot.model.dto.user;

import java.util.List;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.enums.UserRole;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO implements Serializable{
    private String username;
    private String email;
    private List<UserRole> userRoles;
}
