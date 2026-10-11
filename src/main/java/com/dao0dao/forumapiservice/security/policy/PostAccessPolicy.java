package com.dao0dao.forumapiservice.security.policy;


import com.dao0dao.forumapiservice.model.constans.ApiErrorMessage;
import com.dao0dao.forumapiservice.model.entities.PostEntity;
import com.dao0dao.forumapiservice.model.exception.NoAccessException;
import com.dao0dao.forumapiservice.utils.CurrentUser;


public class PostAccessPolicy {
    public static void verifyPostAccess(PostEntity post) {
        var userId = CurrentUser.getUserId();
        var isAdmin = CurrentUser.isAdmin();

        boolean isOwner = post.getAuthor().getId().equals(userId);

        if (!(isAdmin || isOwner)) {
            throw new NoAccessException(ApiErrorMessage.USER_ACCESS_ERROR.getMessage());
        }
    }

    public static boolean canViewDeleted() {
        return CurrentUser.isAdmin();
    }
}