package com.post_hub.iam_service.model.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NoAccessException extends RuntimeException {
    
    public NoAccessException(String message){
        super(message);
    }
}
