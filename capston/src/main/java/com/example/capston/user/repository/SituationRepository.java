package com.example.capston.user.repository;

import com.example.capston.user.domain.SituationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SituationRepository  extends JpaRepository<SituationEntity, Long> {
}
