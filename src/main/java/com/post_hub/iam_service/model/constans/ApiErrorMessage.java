package com.post_hub.iam_service.model.constans;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ApiErrorMessage {
    POST_ERROR_BY_ID("Post with ID: {} was not found"),
    POST_ALREADY_EXIST("Post with title: '%s' already exist"),
    TYPE_MISMATCH("Field '%s' has invalid value type. Expected '%s'.");

    private final String message;

    public String getMessage(Object... args) {
        return String.format(this.message, args);
    }
}
