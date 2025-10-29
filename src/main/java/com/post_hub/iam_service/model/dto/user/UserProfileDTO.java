package com.post_hub.iam_service.model.dto.user;

import java.util.List;
import java.io.Serializable;

import com.post_hub.iam_service.model.enums.UserRole;

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
