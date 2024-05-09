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
    private String weather; //날씨 상태
    private double windChillfactor;
    private double temperature; //온도
    private Long humidity; //습도
    private Long userNumber;

    @Builder
    public WeatherEntity(String latitude, String longitude, LocalDate date, String weather, double windChillfactor ,double temperature, Long humidity, Long userNumber){
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.weather = weather;
        this.windChillfactor = windChillfactor;
        this.temperature = temperature;
        this.humidity = humidity;
        this.userNumber = userNumber;
    }
}
