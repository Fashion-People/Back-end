package com.example.capston.user.repository;

import com.example.capston.user.domain.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface WeatherRepository extends JpaRepository<WeatherEntity, Long> {
    List<WeatherEntity> findByLatitudeAndLongitude(String latitude, String  longitude);
    Optional<WeatherEntity> findByLatitude(String  latitude);
    boolean existsByLatitude(String latitude);
    boolean existsByLongitude(String longitude);
}
