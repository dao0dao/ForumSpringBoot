package com.dao0dao.forumapiservice.model.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message){
        super(message);
    }
}
