package com.post_hub.iam_service.mapper;

import com.post_hub.iam_service.model.dto.role.RoleDTO;
import com.post_hub.iam_service.model.entities.Role;

public class RoleMapper {
    public static RoleDTO toDTO (Role role){
        return RoleDTO
        .builder()
        .active(role.getActive())
        .createdBy(role.getCreatedBy())
        .name(role.getName())
        .userSystemName(role.getUserSystemName())
        .build();
    }
}
