package com.example.capston.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException{
    private ErrorCode errorCode;

    public NotFoundException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
}
