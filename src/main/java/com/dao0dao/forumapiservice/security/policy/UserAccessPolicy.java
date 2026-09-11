package com.dao0dao.forumapiservice.security.policy;

import com.dao0dao.forumapiservice.utils.CurrentUser;



public class UserAccessPolicy {

    public static boolean hasAccess(Integer userId) {
        var currentUserId = CurrentUser.getUserId();
        return CurrentUser.isAdmin() || currentUserId.equals(userId);
    }
}
