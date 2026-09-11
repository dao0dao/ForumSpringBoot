package com.dao0dao.forumapiservice.mapper;


import com.dao0dao.forumapiservice.model.entities.RoleEntity;
import com.dao0dao.forumapiservice.model.enums.UserRole;

public class RoleMapper {
    public static UserRole toUserRole (RoleEntity role){
        return UserRole.fromName(role.getName());
    }
}
