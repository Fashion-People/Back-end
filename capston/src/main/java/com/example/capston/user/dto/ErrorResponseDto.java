package com.example.capston.user.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponseDto {
    private String message;

    public ErrorResponseDto(String message){
        this.message = message;
    }
}
