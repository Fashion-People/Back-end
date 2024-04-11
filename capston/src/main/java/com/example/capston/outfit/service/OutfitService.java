package com.example.capston.outfit.service;

import com.example.capston.outfit.domain.OutfitEntity;
import com.example.capston.result.dto.OutfitResultDto;
import com.example.capston.outfit.repository.OutfitRepository;
import com.example.capston.user.service.Weather.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OutfitService {
    private final OutfitRepository outfitRepository;
    private final WeatherService weatherService;

    @Transactional(readOnly = true)
    public OutfitResultDto getOutfit(String latitude, String longitude){
        Double temp= weatherService.getWeather(latitude, longitude);
        int temperature = temp.intValue();
        OutfitEntity outfitEntity = outfitRepository.findByTemperatureMinLessThanEqualAndTemperatureMaxGreaterThanEqual(temperature,temperature).orElse(null);
        return OutfitResultDto.toDto(outfitEntity);
    }

    //결과 계산 함수
    public Long getNumber(String clothingType){
        List<OutfitEntity> outfitEntities = outfitRepository.findAll();

        for(OutfitEntity outfitEntity : outfitEntities){
            for(String type : outfitEntity.getClothingTypes()){
                if(clothingType.equals(type)){
                    Long outfitNumber = outfitEntity.getOutfitNumber();
                    return outfitNumber;
                }
            }
        }
        return null;
    }
}
