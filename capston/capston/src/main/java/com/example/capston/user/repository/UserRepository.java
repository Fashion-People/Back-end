package com.example.capston.user.repository;

import com.example.capston.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
    //아이디로 회원 정보 조회 (select*from user where userid=?)
    Optional<UserEntity> findByLoginId(String loginId);
    Optional<UserEntity> getByLoginId(String loginId);
    boolean existsByLoginId(String loginId);
}
//UserRepository : JPA에서 지공하는 인터페이스인 JpaRepository를 상속
//JpaRepository : 데이터베이스와 관련된 CRUD를 수행하는 인터페이스
