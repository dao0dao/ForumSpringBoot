package com.dao0dao.forumapiservice.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.dao0dao.forumapiservice.model.enums.UserRole;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLevel {
    String requiredLevel () default UserRole.DEFAULT;
}
