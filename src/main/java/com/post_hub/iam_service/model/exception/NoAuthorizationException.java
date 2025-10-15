package com.post_hub.iam_service.model.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NoAuthorizationException extends RuntimeException {
    public NoAuthorizationException(String message) {
        super(message);
    }
}
