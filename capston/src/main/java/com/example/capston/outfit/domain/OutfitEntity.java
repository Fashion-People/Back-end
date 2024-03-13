package com.example.capston.outfit.domain;

import com.example.capston.user.domain.StringListConverter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name ="temperature_outfit")
public class OutfitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long outfitNumber;
    private int temperatureMin;
    private int temperatureMax;
    @Convert(converter = StringListConverter.class)
    private List<String> clothingTypes;

    @Builder
    public OutfitEntity(Long outfitNumber, int temperatureMin, int temperatureMax, List<String> clothingTypes){
        this.outfitNumber = outfitNumber;
        this.temperatureMin = temperatureMin;
        this.temperatureMax = temperatureMax;
        this.clothingTypes = clothingTypes;
    }
}
