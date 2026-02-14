package com.post_hub.refreshing_knowledge_of_SpringBoot.security.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.exception.NoAccessException;
import com.post_hub.refreshing_knowledge_of_SpringBoot.utils.CurrentUser;

@Aspect
@Component
public class ActiveUserAspect {

    @Before("@annotation(ActiveUser)")
    public void checkAccessLevel() {
        var isActive = CurrentUser.isActive();
        if (!isActive) {
            throw new NoAccessException("Access denied: Super Admin level required");
        }
    }

}
