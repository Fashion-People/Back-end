package com.example.capston.result.repository;

import com.example.capston.result.domain.ResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<ResultEntity, Long> {
    ResultEntity findFirstByOrderByResultNumberDesc();
}
