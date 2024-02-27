package com.example.capston.user.service;

import com.example.capston.exception.ErrorCode;
import com.example.capston.exception.NotFoundException;
import com.example.capston.user.domain.TempEntity;
import com.example.capston.user.dto.Temp.TempRequestDto;
import com.example.capston.user.dto.Temp.TempResponseDto;
import com.example.capston.user.repository.TempRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TempService {
    private final TempRepository tempRepository;

    //데이터 임시 저장
    @Transactional
    public Long tempSave(TempRequestDto tempRequestDto){
        TempEntity tempEntity = TempEntity.builder().imageUrl(tempRequestDto.getImageUrl()).build();
        return tempRepository.save(tempEntity).getTempNumber();
    }

    //임시로 저장된 데이터 조회
    @Transactional(readOnly = true)
    public TempResponseDto getImages(Long tempNumber){
        TempEntity tempEntity = tempRepository.findById(tempNumber).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        return new TempResponseDto(tempEntity);
    }
}

