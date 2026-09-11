package com.dao0dao.forumapiservice.model.dto.user;

import java.util.List;

import com.dao0dao.forumapiservice.model.enums.UserRole;

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
