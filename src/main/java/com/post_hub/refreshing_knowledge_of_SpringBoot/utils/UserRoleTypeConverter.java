package com.post_hub.refreshing_knowledge_of_SpringBoot.utils;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.enums.UserRole;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;

@Convert
public class UserRoleTypeConverter implements AttributeConverter<UserRole, String>{

    @Override
    public String convertToDatabaseColumn(UserRole userRole) {
        return userRole.name();
    }

    @Override
    public UserRole convertToEntityAttribute(String name) {
        return UserRole.fromName(name);
    }
    
}
