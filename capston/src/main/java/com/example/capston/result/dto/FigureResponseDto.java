package com.example.capston.result.dto;

import com.example.capston.result.domain.FigureEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FigureResponseDto {
    Long figureNumber; //적합도 번호
    int index; //배열 인덱스
    int figure; //적합도 수치
    String message; // 메시지
    Long tempNumber; //임시 번호

    @Builder
    public FigureResponseDto(Long figureNumber, int index, int figure, String message, Long tempNumber){
        this.figureNumber = figureNumber;
        this.index = index;
        this.figure = figure;
        this.message = message;
        this.tempNumber = tempNumber;
    }

}
