package com.example.capston.user.repository;

import com.example.capston.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.capston.user.domain.ClothesEntity;

import java.util.List;

public interface ClothesRepository extends JpaRepository<ClothesEntity, Long> {
    List<ClothesEntity> findByUser(UserEntity userEntity);
}
