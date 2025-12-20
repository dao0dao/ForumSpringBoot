package com.post_hub.refreshing_knowledge_of_SpringBoot.model.constans;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ApiMessages {
    TOKEN_CREATED_OR_UPDATED("Token created/updated successfully"),
    UNAUTHORIZED("Unauthorized access")
    ;

    private final String message;
}
