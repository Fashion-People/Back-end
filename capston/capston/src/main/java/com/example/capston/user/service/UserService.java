package com.example.capston.user.service;

import com.example.capston.config.JwtProvider;
import com.example.capston.user.dto.UserRequestDto;
import com.example.capston.user.dto.UserResponseDto;
import com.example.capston.user.domain.UserEntity;
import com.example.capston.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.spel.ast.StringLiteral;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service //해당 클래스가 Service로 사용되고 있음을 나타냄
@RequiredArgsConstructor //생성자 자동 생성. 의존성 주입(생성자 주입) 받음
public class UserService {

    private final UserRepository userRepository; //UserRepository를 사용하여 DB 작업 수행
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    //회원가입
   @Transactional
   public UserResponseDto save(UserRequestDto userRequestDto) {
        String encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());
        UserEntity userEntity = UserEntity.builder()
                .userName(userRequestDto.getUserName())
                .loginId(userRequestDto.getLoginId())
                .password(encodedPassword)
                .email(userRequestDto.getEmail())
                .style1(userRequestDto.getStyle1())
                .style2(userRequestDto.getStyle2())
                .style3(userRequestDto.getStyle3())
                .style4(userRequestDto.getStyle4())
                .roles(Collections.singletonList("USER"))
                .build();
        userEntity = userRepository.save(userEntity);
        UserResponseDto userResponseDto = UserResponseDto.toDto(userEntity);
        return userResponseDto;
    }
    //로그인
    @Transactional
    public String login(String loginId, String password) throws RuntimeException{
        UserEntity userEntity = userRepository.findByLoginId(loginId).orElseThrow(()->new NoSuchElementException("로그인 실패"));
        if(!passwordEncoder.matches(password, userEntity.getPassword())){
            throw new NoSuchElementException("로그인 실패");
        }
        String token = jwtProvider.createToken(String.valueOf(userEntity.getUserId()),userEntity.getRoles());
        return token;
    }

    //회원 조회
    @Transactional
    public UserResponseDto getUser(String loginID){
        UserEntity userEntity = userRepository.findByLoginId(loginID).orElseThrow(() -> new NoSuchElementException("해당 유저가 존재하지 않습니다"));
        return UserResponseDto.toDto(userEntity);
    }

}
