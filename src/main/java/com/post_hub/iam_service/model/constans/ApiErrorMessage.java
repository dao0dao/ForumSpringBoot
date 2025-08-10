package com.post_hub.iam_service.model.constans;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ApiErrorMessage {
    POST_ERROR_BY_ID("Post with ID: %d was not found");

    private final String message;

    public String getMessage(Integer id) {
        return String.format(this.message, id);
    }
}
