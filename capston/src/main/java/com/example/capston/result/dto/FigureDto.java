package com.example.capston.result.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FigureDto {
        Long clothesNumber; //옷 번호
        int figure; //적합도 수치
        String message; // 메시지
        Long tempNumber; //이미지 리스트 번호
        Long aiId;


    @Builder
    public FigureDto(Long clothesNumber, int figure, String message, Long tempNumber, Long aiId){
        this.clothesNumber = clothesNumber;
        this.figure = figure;
        this.message = message;
        this.tempNumber = tempNumber;
        this.aiId = aiId;
    }

}
