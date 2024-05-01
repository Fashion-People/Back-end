package com.example.capston.user.domain;

import lombok.Builder;
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
    private Long userNumber;

    @Builder
    public WeatherEntity(String latitude, String longitude, LocalDate date, String weather, String icon, double temperature, Long userNumber){
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.weather = weather;
        this.icon = icon;
        this.temperature = temperature;
        this.userNumber = userNumber;
    }
}
