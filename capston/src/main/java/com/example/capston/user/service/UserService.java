package com.example.capston.user.service;

import com.example.capston.config.JwtProvider;
import com.example.capston.exception.DuplicateException;
import com.example.capston.exception.ErrorCode;
import com.example.capston.exception.NotFoundException;
import com.example.capston.exception.UnauthorizedException;
import com.example.capston.user.dto.User.UserRequestDto;
import com.example.capston.user.dto.User.UserResponseDto;
import com.example.capston.user.domain.UserEntity;
import com.example.capston.user.dto.User.UserUpdateDto;
import com.example.capston.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    //회원가입
   @Transactional
   public UserResponseDto save(UserRequestDto userRequestDto) {
        String encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());
        if(userRepository.existsByLoginId(userRequestDto.getLoginId())){
            throw new DuplicateException(ErrorCode.USER_DUPLICATE_LOGINID);
        }
        if(userRepository.existsByEmail(userRequestDto.getEmail())){
            throw new DuplicateException(ErrorCode.USER_DUPLICATE_EMAIL);
        }
       List<String> styles = Arrays.asList(
               userRequestDto.getStyle1(),
               userRequestDto.getStyle2(),
               userRequestDto.getStyle3(),
               userRequestDto.getStyle4()
       );
        UserEntity userEntity = UserEntity.builder()
                .name(userRequestDto.getName())
                .loginId(userRequestDto.getLoginId())
                .password(encodedPassword)
                .email(userRequestDto.getEmail())
                .style(styles)
                .roles(Collections.singletonList("USER"))
                .build();
        userEntity = userRepository.save(userEntity);
        UserResponseDto userResponseDto = UserResponseDto.toDto(userEntity);
        return userResponseDto;
    }
    //로그인
    @Transactional
    public String login(String loginId, String password) throws RuntimeException{
        UserEntity userEntity = userRepository.findByLoginId(loginId).orElseThrow(()->new UnauthorizedException(ErrorCode.LOGINID_NOT_FOUND));
        if(!passwordEncoder.matches(password, userEntity.getPassword())){
            throw new UnauthorizedException(ErrorCode.PASSWORD_NOT_MATCHED);
        }
        String token = jwtProvider.createToken(String.valueOf(userEntity.getUserNumber()),userEntity.getRoles());
        return token;
    }
    //회원 조회
    @Transactional
    public UserResponseDto getUser(Long userNumber){
        UserEntity userEntity = userRepository.findById(userNumber).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        return UserResponseDto.toDto(userEntity);
    }

    //회원 수정
    @Transactional
    public Long update(Long userNumber, UserUpdateDto userUpdateDto){
       UserEntity userEntity = userRepository.findById(userNumber).orElseThrow(() -> new NoSuchElementException(""));
         if(!userEntity.getUsername().equals(userUpdateDto.getName())&&userRepository.existsByLoginId(userUpdateDto.getLoginId())){
           throw new DuplicateException(ErrorCode.USER_DUPLICATE_LOGINID);
        }
        List<String> styles = Arrays.asList(
                userUpdateDto.getStyle1(),
                userUpdateDto.getStyle2(),
                userUpdateDto.getStyle3(),
                userUpdateDto.getStyle4()
        );
        userEntity.update(userUpdateDto.getLoginId(),userUpdateDto.getName(), userUpdateDto.getEmail(), styles);
        return userNumber;
    }

}
