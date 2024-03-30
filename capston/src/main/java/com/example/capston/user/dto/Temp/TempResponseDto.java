package com.example.capston.user.dto.Temp;

import com.example.capston.user.domain.TempEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
//AI에 보낼 DTO
public class TempResponseDto {
    private Long tempNumber;
    private List<String> imageUrl;

    @Builder
    public TempResponseDto(Long tempNumber, List<String> imageUrl){
        this.tempNumber = tempNumber;
        this.imageUrl = imageUrl;
    }
}
