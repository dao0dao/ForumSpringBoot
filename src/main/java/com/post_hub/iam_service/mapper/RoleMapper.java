package com.post_hub.iam_service.mapper;


import com.post_hub.iam_service.model.entities.Role;
import com.post_hub.iam_service.model.enums.UserRole;

public class RoleMapper {
    public static UserRole toUserRole (Role role){
        return UserRole.fromName(role.getName());
    }
}
