package com.example.capston.result.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResultDto { //User에서 number랑 취향 4가지만 가져온 DTO
    private Long userNumber;
    private String style1; //취향1
    private String style2; //취향2
    private String style3; //취향3
    private String style4; //취향4

    @Builder
    public UserResultDto(Long userNumber, String style1, String style2, String style3, String style4){
        this.userNumber = userNumber;
        this.style1 = style1;
        this.style2 = style2;
        this.style3 = style3;
        this.style4 = style4;
    }
}
