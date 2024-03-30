package com.example.capston.result.service;

import com.example.capston.user.domain.UserEntity;
import com.example.capston.result.dto.UserResultDto;
import com.example.capston.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserResultService {
    private final UserRepository userRepository;

    @Transactional
    public UserResultDto getResult(Long userNumber){
        UserEntity userEntity = userRepository.findById(userNumber).orElseThrow();

        UserResultDto userResultDto = UserResultDto.builder()
                .userNumber(userEntity.getUserNumber())
                .style1(userEntity.getStyle1())
                .style2(userEntity.getStyle2())
                .style3(userEntity.getStyle3())
                .style4(userEntity.getStyle4())
                .build();
        return userResultDto;
    }
}
