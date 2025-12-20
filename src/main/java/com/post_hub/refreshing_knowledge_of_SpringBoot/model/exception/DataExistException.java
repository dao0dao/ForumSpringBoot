package com.post_hub.refreshing_knowledge_of_SpringBoot.model.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataExistException extends RuntimeException {
    
    public DataExistException(String message){
        super(message);
    }
}
