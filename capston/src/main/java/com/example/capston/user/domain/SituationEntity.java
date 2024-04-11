package com.example.capston.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "situation")
@Getter
@NoArgsConstructor
public class SituationEntity {
    @Id
    @Column(name="situationNumber")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long situationNumber;
    private String style;
    @Convert(converter = StringListConverter.class)
    private List<String> situations;

    @Builder
    public SituationEntity(Long situationNumber, String style, List<String> situations){
        this.situationNumber = situationNumber;
        this.style = style;
        this.situations = situations;
    }
}
