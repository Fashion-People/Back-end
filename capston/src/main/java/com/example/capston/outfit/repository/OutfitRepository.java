package com.example.capston.outfit.repository;

import com.example.capston.outfit.domain.OutfitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OutfitRepository extends JpaRepository<OutfitEntity, Long> {
  Optional<OutfitEntity> findByTemperatureMinLessThanEqualAndTemperatureMaxGreaterThanEqual(int temperatureMin, int temperatureMax);
}
