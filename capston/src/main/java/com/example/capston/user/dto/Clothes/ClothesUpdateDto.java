package com.example.capston.user.dto.Clothes;

import com.example.capston.user.domain.ClothesEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClothesUpdateDto {
    private String description;

    @Builder
    public ClothesUpdateDto(String description){
        this.description = description;
    }

    public ClothesEntity toEntity(){
        return ClothesEntity.builder()
                .description(description)
                .build();
    }
}
