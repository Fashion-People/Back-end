package com.example.capston.result.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Table(name = "figure")
@NoArgsConstructor
public class FigureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long clothesNumber;
    private Long tempNumber; //임시 번호
    private String imageUrl; //이미지 url
    private int figure; //적합도 수치
    private String message; //적합도 메시지
    @Builder
    public FigureEntity(Long id, Long clothesNumber, Long tempNumber, String imageUrl, int figure, String message){
        this.id = id;
        this.clothesNumber = clothesNumber;
        this.figure = figure;
        this.imageUrl = imageUrl;
        this.message = message;
        this.tempNumber = tempNumber;
    }
}
