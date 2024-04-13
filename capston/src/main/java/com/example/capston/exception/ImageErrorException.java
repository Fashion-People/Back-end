package com.example.capston.exception;

import lombok.Getter;

@Getter
public class ImageErrorException extends RuntimeException {
    private ErrorCode errorCode;
    private int number;

    public ImageErrorException(ErrorCode errorCode, int number){
        this.errorCode = errorCode;
        this.number = number;
    }
}
