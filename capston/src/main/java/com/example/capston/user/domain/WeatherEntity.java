package com.example.capston.user.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "weather")
@NoArgsConstructor
public class WeatherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weatherNumber;
    private String latitude;
    private String longitude;
    private LocalDate date;
    private String weather;
    private String icon;
    private double temperature;

    public WeatherEntity(WeatherEntity weatherEntity){
        this.date = weatherEntity.getDate();
        this.latitude = weatherEntity.getLatitude();
        this.longitude = weatherEntity.getLongitude();
        this.weather = weatherEntity.getWeather();
        this.icon = weatherEntity.getIcon();
        this.temperature = weatherEntity.getTemperature();
    }
}
