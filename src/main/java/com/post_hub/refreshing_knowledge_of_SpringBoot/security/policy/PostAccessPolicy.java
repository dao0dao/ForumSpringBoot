package com.post_hub.refreshing_knowledge_of_SpringBoot.security.policy;


import com.post_hub.refreshing_knowledge_of_SpringBoot.model.constans.ApiErrorMessage;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.entities.PostEntity;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.exception.NoAccessException;
import com.post_hub.refreshing_knowledge_of_SpringBoot.utils.CurrentUser;


public class PostAccessPolicy {
    public static void verifyPostAccess(PostEntity post) {
        var userId = CurrentUser.getUserId();
        var isAdmin = CurrentUser.isAdmin();

        boolean isOwner = post.getUser().getId().equals(userId);

        if (!(isAdmin || isOwner)) {
            throw new NoAccessException(ApiErrorMessage.USER_ACCESS_ERROR.getMessage());
        }
    }
}