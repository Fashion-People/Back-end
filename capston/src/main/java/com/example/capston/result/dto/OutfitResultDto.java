package com.example.capston.result.dto;

import com.example.capston.outfit.domain.OutfitEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OutfitResultDto { //Outfit에서 outfitNumber랑 옷 리스트만 가져온 DTO
    private Long outfitNumber;
    private List<String> clothingTypes;

    @Builder
    public OutfitResultDto(Long outfitNumber, List<String> clothingTypes){
        this.outfitNumber = outfitNumber;
        this.clothingTypes = clothingTypes;
    }

    public static OutfitResultDto toDto(OutfitEntity outfitEntity){
        return OutfitResultDto.builder()
                .outfitNumber(outfitEntity.getOutfitNumber())
                .clothingTypes(outfitEntity.getClothingTypes())
                .build();
    }
}
