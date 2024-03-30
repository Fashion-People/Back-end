package com.example.capston.user.dto.Temp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
//프론트로부터 받아 올 데이터를 담은 DTO (프론트에 요청할 DTO)
public class TempRequestDto {
    private Long tempNumber;
    private List<String> imageUrl; //이미지 url 리스트
    private String token;
    private String latitude;
    private String longitude;

    @Builder
    public TempRequestDto(Long tempNumber, List<String> imageUrl, String token, String latitude, String longitude){
        this.tempNumber = tempNumber;
        this.imageUrl = imageUrl;
        this.token = token;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}