package com.example.capston.result.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AiRequestDto { //AI에서 받아온 데이터를 저장하는 DTO
    Long tempNumber;
    Long clothesNumber;
    String clothesStyle; //AI에서 받아온 옷 스타일
    String clothesType; //옷 종류

    @Builder
    public AiRequestDto(Long tempNumber, Long clothesNumber, String clothesStyle, String clothesType){
        this.tempNumber = tempNumber;
        this.clothesNumber = clothesNumber;
        this.clothesStyle = clothesStyle;
        this.clothesType = clothesType;
    }
}
