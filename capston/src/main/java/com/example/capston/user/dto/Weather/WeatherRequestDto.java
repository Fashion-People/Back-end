package com.example.capston.user.dto.Weather;

import lombok.Builder;
import lombok.Getter;

@Getter
public class WeatherRequestDto {
    private String latitude;
    private String longitude;

    @Builder
    public WeatherRequestDto(String latitude, String longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
