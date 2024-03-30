package com.example.capston.result.repository;

import com.example.capston.result.domain.FigureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FigureRepository extends JpaRepository<FigureEntity, Long> {
    List<FigureEntity> findByTempNumber(Long tempNumber);
}
