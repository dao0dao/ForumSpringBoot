package com.post_hub.refreshing_knowledge_of_SpringBoot.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.enums.UserRole;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLevel {
    String requiredLevel () default UserRole.DEFAULT;
}
