package com.example.capston.exception;

import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException{
    private final ErrorCode errorCode;

    public UnauthorizedException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
}
