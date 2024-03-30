package com.example.capston.result.domain;

import com.example.capston.user.domain.StringListConverter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Table(name = "result")
@NoArgsConstructor
public class ResultEntity { //결과를 저장하는 Entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long resultNumber;
    @Convert(converter = StringListConverter.class)
    private List<String> clothingTypes;
    private String style1; //취향1
    private String style2; //취향2
    private String style3; //취향3
    private String style4; //취향4

    @Builder
    public ResultEntity(Long resultNumber, List<String> clothingTypes, String style1, String style2, String style3, String style4){
        this.resultNumber = resultNumber;
        this.clothingTypes = clothingTypes;
        this.style1 = style1;
        this.style2 = style2;
        this.style3 = style3;
        this.style4 = style4;
    }

}
