package com.example.capston.user.repository;

import com.example.capston.user.domain.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WeatherRepository extends JpaRepository<WeatherEntity, Long> {
    List<WeatherEntity> findByDateAndLatitudeAndLongitude(LocalDate today, String latitude, String  longitude);
    WeatherEntity getByDateAndLatitudeAndLongitude(LocalDate today, String latitude, String  longitude);
    WeatherEntity getByDateAndUserNumber(LocalDate today, Long userNumber);
    boolean existsByDateAndLatitudeAndLongitude(LocalDate today, String latitude, String  longitude);
    boolean existsByDateAndUserNumber(LocalDate today, Long userNumber);
    List<WeatherEntity> findByUserNumberAndDateOrderByWeatherNumberDesc(Long userNumber, LocalDate today);
}
