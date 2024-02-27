package com.example.capston.exception;

import lombok.Getter;

@Getter
public class NotPermittedException extends RuntimeException {
    private ErrorCode errorCode;
    public NotPermittedException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
}
