package com.example.capston.result.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FigureResponseDto {
    Long clothesNumber; //옷 번호
    String imageUrl; //이미지 url
    int figure; //적합도 수치
    String message; // 메시지

    @Builder
    public FigureResponseDto(Long clothesNumber, String imageUrl, int figure, String message){
        this.clothesNumber = clothesNumber;
        this.imageUrl = imageUrl;
        this.figure = figure;
        this.message = message;
    }
}
