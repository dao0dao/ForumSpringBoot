package com.post_hub.refreshing_knowledge_of_SpringBoot.security.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.enums.UserRole;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.exception.NoAccessException;
import com.post_hub.refreshing_knowledge_of_SpringBoot.security.annotation.AccessLevel;
import com.post_hub.refreshing_knowledge_of_SpringBoot.utils.CurrentUser;

@Aspect
@Component
public class AccessLevelAspect {

    @Before("@annotation(accessLevel)")
    public void checkAccessLevel(AccessLevel accessLevel) {
        var requiredRole = UserRole.fromName(accessLevel.requiredLevel());

        System.out.println("=================================================");
        System.out.println("Required role: " + requiredRole);

        if (requiredRole.getRole() == UserRole.USER.getRole()) {
            return;
        }

        boolean isAdmin = false;
        boolean isSuperAdmin = false;

        var userRoles = CurrentUser.getUserDetails().getAuthorities();

        for (GrantedAuthority authority : userRoles) {
            if (authority.getAuthority().equals(UserRole.ADMIN.getRole())) {
                isAdmin = true;
            }
            if (authority.getAuthority().equals(UserRole.SUPER_ADMIN.getRole())) {
                isSuperAdmin = true;
            }
        }

        if (requiredRole.getRole() == UserRole.SUPER_ADMIN.getRole() && !isSuperAdmin) {
            throw new NoAccessException("Access denied: Super Admin level required");
        }
        if (requiredRole.getRole() == UserRole.ADMIN.getRole() && !isAdmin && !isSuperAdmin) {
            throw new NoAccessException("Access denied: Admin level required");
        }
    }
}
