package com.example.capston.exception;

import lombok.Getter;

@Getter
public class DuplicateException extends RuntimeException {
    private ErrorCode errorCode;

    public DuplicateException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
}
