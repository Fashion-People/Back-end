package com.example.capston.exception;

import lombok.Getter;

@Getter
public class ImageErrorException extends RuntimeException {
    private ErrorCode errorCode;

    public ImageErrorException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
}
