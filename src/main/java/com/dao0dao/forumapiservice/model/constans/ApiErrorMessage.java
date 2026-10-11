package com.dao0dao.forumapiservice.model.constans;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ApiErrorMessage {
    COMMENT_ERROR_BY_ID("Comment with ID: '%s' was not found"),

    POST_ERROR_BY_ID("Post with ID: '%s' was not found"),
    POST_ALREADY_EXIST("Post with title: '%s' already exist"),

    ROLE_ERROR("Role: '%s' was not found"),

    SORT_FIELD_NOT_ALLOWED("Sorting by field '%s' is not allowed"),

    TYPE_MISMATCH("Field '%s' has invalid value type. Expected '%s'."),

    USER_ACCESS_ERROR("Access denied"),
    USER_ERROR_BY_ID("User with ID: '%s' was not found"),
    USER_NOT_FOUND("User was not found"),

    ERROR_DURNING_JWT_PROCESSING("An unexpected error occurred durning jwt processing"),
    TOKEN_EXPIRED("Token expired"),
    UNEXPECTED_ERROR_OCCUR("An expected error occurred. Pleas try again later")
    ;

    private final String message;

    public String getMessage(Object... args) {
        return String.format(this.message, args);
    }
}
