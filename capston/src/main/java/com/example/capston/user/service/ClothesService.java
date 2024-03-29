package com.example.capston.user.service;

import com.example.capston.exception.ErrorCode;
import com.example.capston.exception.NotFoundException;
import com.example.capston.exception.NotPermittedException;
import com.example.capston.user.domain.ClothesEntity;
import com.example.capston.user.domain.UserEntity;
import com.example.capston.user.dto.Clothes.ClothesListResponseDto;
import com.example.capston.user.dto.Clothes.ClothesResponseDto;
import com.example.capston.user.dto.Clothes.ClothesSaveDto;
import com.example.capston.user.dto.Clothes.ClothesUpdateDto;
import com.example.capston.user.repository.ClothesRepository;
import com.example.capston.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClothesService {

    private final UserRepository userRepository;
    private final ClothesRepository clothesRepository;
    //옷 데이터 저장
   @Transactional
    public Long save(Long number, ClothesSaveDto clothesSaveDto) {
       UserEntity userEntity = userRepository.findById(number).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
       return clothesRepository.save(ClothesEntity.builder()
                                .user(userEntity)
                                .description(clothesSaveDto.getDescription())
                                .imageUrl(clothesSaveDto.getImageUrl())
                                .build()).getClothesNumber();
   }
   //옷 데이터 조회
    @Transactional(readOnly = true)
    public ClothesResponseDto getClothes(Long clothesNumber){
       ClothesEntity clothesEntity = clothesRepository.findById(clothesNumber).orElseThrow(() -> new NotFoundException(ErrorCode.CLOTHES_NOT_FOUND));
       return new ClothesResponseDto(clothesEntity);
    }
   //옷 리스트 조회
   @Transactional(readOnly = true)
    public List<ClothesListResponseDto> getAllClothes(String loginId){
       UserEntity userEntity = userRepository.findByLoginId(loginId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
       List<ClothesListResponseDto> clothesListResponseDtos =  clothesRepository.findByUser(userEntity)
                                                                .stream().map(ClothesListResponseDto::new)
                                                                 .collect(Collectors.toList());
        return clothesListResponseDtos;
   }
   //옷 데이터 수정
    @Transactional
    public ClothesResponseDto update(Long number, ClothesUpdateDto clothesUpdateDto, Long userNumber){
       ClothesEntity clothesEntity = clothesRepository.findById(number).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
       if(clothesEntity.getUser().getUserNumber() != userNumber){
           throw new NotPermittedException(ErrorCode.UPDATE_NOT_PERMITTED);
       }
       clothesEntity.update(clothesUpdateDto.getDescription());
       return new ClothesResponseDto(clothesEntity);
    }
   //옷 데이터 삭제
   @Transactional
    public void delete(Long number, Long userNumber){
       ClothesEntity clothesEntity = clothesRepository.findById(number).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
       if(clothesEntity.getUser().getUserNumber() != userNumber){
           throw new NotPermittedException(ErrorCode.DELETE_NOT_PERMITTED);
       }
       clothesRepository.delete(clothesEntity);
   }
}
