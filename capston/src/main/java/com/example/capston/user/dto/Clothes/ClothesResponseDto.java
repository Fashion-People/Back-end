package com.example.capston.user.dto.Clothes;

import com.example.capston.user.domain.ClothesEntity;
import lombok.Getter;

@Getter
public class ClothesResponseDto {
    private Long clothesNumber;
    private String description;
    private String imageUrl;
    private Long userNumber;

    public ClothesResponseDto(ClothesEntity clothesEntity){
        this.clothesNumber = clothesEntity.getClothesNumber();
        this.description = clothesEntity.getDescription();
        this.imageUrl = clothesEntity.getImageUrl();
        this.userNumber = clothesEntity.getUser().getUserNumber();
    }
}
