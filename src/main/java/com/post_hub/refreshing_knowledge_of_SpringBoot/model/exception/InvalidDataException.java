package com.post_hub.refreshing_knowledge_of_SpringBoot.model.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message){
        super(message);
    }
}
