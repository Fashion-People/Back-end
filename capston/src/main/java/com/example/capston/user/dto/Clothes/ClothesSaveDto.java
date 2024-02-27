package com.example.capston.user.dto.Clothes;

import com.example.capston.user.domain.ClothesEntity;
import com.example.capston.user.repository.ClothesRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClothesSaveDto {
    private String description;
    private String imageUrl;

    @Builder
    public ClothesSaveDto(String description, String imageUrl){
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public ClothesEntity toEntity(){
        return ClothesEntity.builder()
                .description(description)
                .imageUrl(imageUrl)
                .build();
    }
}
