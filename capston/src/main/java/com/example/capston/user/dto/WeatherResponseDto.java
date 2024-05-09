package com.example.capston.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class WeatherResponseDto {
    private String weather;
    private double windChillfactor;
    private double temperature;
    private Long humidity;

    @Builder
    public WeatherResponseDto(String weather, double windChillfactor, double temperature, Long humidity){
        this.weather = weather;
        this.windChillfactor = windChillfactor;
        this.temperature = temperature;
        this.humidity = humidity;
    }
}
