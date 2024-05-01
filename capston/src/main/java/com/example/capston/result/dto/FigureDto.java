package com.example.capston.result.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FigureDto {
        Long clothesNumber; //옷 번호
        int figure; //적합도 수치
        String imageUrl;
        String message; // 메시지
        Long tempNumber; //이미지 리스트 번호


    @Builder
    public FigureDto(Long clothesNumber, int figure, String imageUrl, String message, Long tempNumber){
        this.clothesNumber = clothesNumber;
        this.figure = figure;
        this.imageUrl = imageUrl;
        this.message = message;
        this.tempNumber = tempNumber;
    }

}
