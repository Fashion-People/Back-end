package com.example.capston.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "clothes")
@NoArgsConstructor
public class ClothesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clothesNumber; //옷 번호

    @Column(name="name", nullable = false)
    private String description; //옷 설명

    @Column(name = "image", nullable = false)
    private String imageUrl; //이미지 url

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_number")
    private UserEntity user; //user 테이블 조인

    @Builder ClothesEntity(Long clothesNumber, String description, String imageUrl, UserEntity user){
        this.clothesNumber = clothesNumber;
        this.description = description;
        this.imageUrl = imageUrl;
        this.user = user;
    }
    public void upload(String description, String imageUrl){
        this.description = description;
        this.imageUrl = imageUrl;
    }
}
