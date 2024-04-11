package com.example.capston.user.service;

import com.example.capston.user.domain.SituationEntity;
import com.example.capston.user.repository.SituationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SituationService {
    private final SituationRepository situationRepository;

    public SituationEntity getSituationEntity(String situation){
        List<SituationEntity> situationEntityList = situationRepository.findAll();
        for(SituationEntity situationEntity : situationEntityList){
            List<String> situations = situationEntity.getSituations();
            for(String s : situations){
                if(s.equals(situation)){
                    return situationEntity;
                }
            }
        }
        return null;
    }
}
