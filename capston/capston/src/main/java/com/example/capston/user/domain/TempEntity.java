package com.example.capston.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "temp")
@Getter
@NoArgsConstructor
public class  TempEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tempNumber;

    @Convert(converter = StringListConverter.class)
    @Column(name = "image")
    private List<String> imageUrl;

    @Builder
    public TempEntity(Long tempNumber, List<String> imageUrl){
        this.tempNumber = tempNumber;
        this.imageUrl = imageUrl;
    }
}
