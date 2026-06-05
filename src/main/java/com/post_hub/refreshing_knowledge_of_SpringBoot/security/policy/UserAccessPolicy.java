package com.post_hub.refreshing_knowledge_of_SpringBoot.security.policy;

import com.post_hub.refreshing_knowledge_of_SpringBoot.utils.CurrentUser;



public class UserAccessPolicy {

    public static boolean hasAccess(Integer userId) {
        var currentUserId = CurrentUser.getUserId();
        return CurrentUser.isAdmin() || currentUserId.equals(userId);
    }
}
