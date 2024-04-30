package com.example.capston.exception;

import lombok.Getter;

@Getter
public class InternalServerErrorException extends RuntimeException {
    private ErrorCode errorCode;
    public InternalServerErrorException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
}
