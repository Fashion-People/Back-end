package com.example.capston.user.repository;

import com.example.capston.user.domain.ClothesEntity;
import com.example.capston.user.domain.TempEntity;
import com.example.capston.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TempRepository extends JpaRepository<TempEntity, Long> {
}
