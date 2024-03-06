package com.example.capston.user.dto;

import lombok.Getter;

@Getter
public class ResponseDto<T> {
    private T data;
    private String msg;

    public ResponseDto(T data, String msg){
        this.data = data;
        this.msg = msg;
    }
}
