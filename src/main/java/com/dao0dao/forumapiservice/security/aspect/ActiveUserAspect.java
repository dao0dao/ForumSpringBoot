package com.dao0dao.forumapiservice.security.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.dao0dao.forumapiservice.model.exception.NoAccessException;
import com.dao0dao.forumapiservice.utils.CurrentUser;

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
