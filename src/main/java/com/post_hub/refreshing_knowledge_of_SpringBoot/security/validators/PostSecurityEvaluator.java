package com.post_hub.refreshing_knowledge_of_SpringBoot.security.validators;

import org.springframework.stereotype.Component;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.constans.ApiErrorMessage;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.entities.Post;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.enums.UserRole;
import com.post_hub.refreshing_knowledge_of_SpringBoot.model.exception.NoAccessException;
import com.post_hub.refreshing_knowledge_of_SpringBoot.utils.CurrentUser;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostSecurityEvaluator {
    public void verifyPostAccess(Post post) {
        var userId = CurrentUser.getUserId();
        var userDetails = CurrentUser.getUserDetails();

        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals(UserRole.ADMIN.getRole()) ||
                        auth.getAuthority().equals(UserRole.SUPER_ADMIN.getRole()));

        boolean isOwner = post.getUser().getId().equals(userId);

        if (!(isAdmin || isOwner)) {
            throw new NoAccessException(ApiErrorMessage.USER_ACCESS_ERROR.getMessage());
        }
    }
}