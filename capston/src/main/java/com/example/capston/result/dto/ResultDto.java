package com.example.capston.result.dto;

import com.example.capston.result.domain.ResultEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ResultDto { //OutfitResultDto랑 UserResultDto를 모두 담은 DTO
    Long resultNumber;
    private List<String> clothingTypes;
    private String style1; //취향1
    private String style2; //취향2
    private String style3; //취향3
    private String style4; //취향4

    @Builder
    public ResultDto(Long resultNumber, List<String> clothingTypes, String style1, String style2, String style3, String style4){
        this.resultNumber = resultNumber;
        this.clothingTypes = clothingTypes;
        this.style1 = style1;
        this.style2 = style2;
        this.style3 = style3;
        this.style4 = style4;
    }
    public static ResultDto toDto(ResultEntity resultEntity){
        return ResultDto.builder()
                .resultNumber(resultEntity.getResultNumber())
                .clothingTypes(resultEntity.getClothingTypes())
                .style1(resultEntity.getStyle1())
                .style2(resultEntity.getStyle2())
                .style3(resultEntity.getStyle3())
                .style4(resultEntity.getStyle4())
                .build();
    }
}
