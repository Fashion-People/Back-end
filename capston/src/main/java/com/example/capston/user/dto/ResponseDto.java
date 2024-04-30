package com.example.capston.user.dto;

import lombok.Getter;

@Getter
public class ResponseDto<T> {
    private T data;
    private String message;

    public ResponseDto(T data, String message){
        this.data = data;
        this.message = message;
    }
}
