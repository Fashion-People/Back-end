package com.example.capston.user.dto.Temp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class TempRequestDto {

    private List<String> imageUrl; //이미지 url 리스트

    @Builder
    public TempRequestDto(List<String> imageUrl){
        this.imageUrl = imageUrl;
    }
}
