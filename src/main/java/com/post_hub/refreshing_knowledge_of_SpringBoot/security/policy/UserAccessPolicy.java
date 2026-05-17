package com.post_hub.refreshing_knowledge_of_SpringBoot.security.policy;

import com.post_hub.refreshing_knowledge_of_SpringBoot.utils.CurrentUser;

import org.springframework.stereotype.Component;

@Component
public class UserAccessPolicy {

    public boolean hasAccess(Integer userId) {
        var currentUserId = CurrentUser.getUserId();
        return CurrentUser.isSuperAdmin() || currentUserId.equals(userId);
    }
}
