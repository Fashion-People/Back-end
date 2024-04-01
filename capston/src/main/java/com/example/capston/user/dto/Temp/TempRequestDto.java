package com.example.capston.user.dto.Temp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
//프론트로부터 받아 올 데이터를 담은 DTO (프론트에 요청할 DTO)
public class TempRequestDto {
    private List<String> imageUrl; //이미지 url 리스트
    private String latitude;
    private String longitude;

    @Builder
    public TempRequestDto(List<String> imageUrl, String latitude, String longitude){
        this.imageUrl = imageUrl;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}