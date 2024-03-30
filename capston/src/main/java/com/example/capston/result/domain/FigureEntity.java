package com.example.capston.result.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "figure")
@NoArgsConstructor
public class FigureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    Long figureNumber;
    int figure; //적합도 수치
    String message; //적합도 메시지
    Long tempNumber; //임시 번호

    @Builder
    public FigureEntity(Long Id, Long figureNumber, int figure, String message, Long tempNumber){
        this.Id = Id;
        this.figureNumber = figureNumber;
        this.figure = figure;
        this.message = message;
        this.tempNumber = tempNumber;
    }
}
