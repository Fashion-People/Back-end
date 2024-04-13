package com.example.capston.user.dto;

import lombok.Getter;

@Getter
public class ImageErrorResponseDto {
        private String message;
        private int number;

        public ImageErrorResponseDto(String message, int number){
            this.message = message;
            this.number = number;
        }
}
