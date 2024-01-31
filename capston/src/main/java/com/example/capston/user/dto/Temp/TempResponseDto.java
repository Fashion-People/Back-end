package com.example.capston.user.dto.Temp;

import com.example.capston.user.domain.TempEntity;
import lombok.Getter;

import java.util.List;

@Getter
public class TempResponseDto {
    private Long tempNumber;
    private List<String> imageUrl;

    public TempResponseDto(TempEntity tempEntity){
        this.tempNumber = tempEntity.getTempNumber();
        this.imageUrl = tempEntity.getImageUrl();
    }
}
