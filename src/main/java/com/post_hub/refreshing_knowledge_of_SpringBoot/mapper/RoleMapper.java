package com.post_hub.refreshing_knowledge_of_SpringBoot.mapper;


import com.post_hub.refreshing_knowledge_of_SpringBoot.model.entities.Role;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.enums.UserRole;

public class RoleMapper {
    public static UserRole toUserRole (Role role){
        return UserRole.fromName(role.getName());
    }
}
