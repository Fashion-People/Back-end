package com.example.capston.user.controller;

import com.example.capston.config.JwtProvider;
import com.example.capston.user.dto.*;
import com.example.capston.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor //의존성 주입을 받음. UserService에 대한 멤버 사용 가능
public class UserController {

    private final UserService userService; //Service 클래스 활용

    //회원 가입
    @PostMapping("/join")
    public ResponseEntity<UserResponseDto> joinUser(@RequestBody UserRequestDto userRequestDto){
        UserResponseDto userResponseDto = userService.save(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }
    //로그인
    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestParam("loginId") String loginId, @RequestParam("password") String password){
        String token = userService.login(loginId, password);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    //회원 조회
    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestParam String loginId){
        return new ResponseEntity<>(userService.getUser(loginId), HttpStatus.OK);
    }

}
