package com.example.capston.user.controller;

import com.example.capston.config.JwtProvider;
import com.example.capston.user.dto.User.UserRequestDto;
import com.example.capston.user.dto.User.UserResponseDto;
import com.example.capston.user.dto.User.UserUpdateDto;
import com.example.capston.user.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
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

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @ApiOperation(value = "회원 가입", notes = "새로운 회원 정보 입력 API")
    @ApiResponse(code=200, message="회원가입 성공")
    @PostMapping("/join")
    public ResponseEntity<UserResponseDto> joinUser(@RequestBody UserRequestDto userRequestDto){
        UserResponseDto userResponseDto = userService.save(userRequestDto);
        log.info("회원 가입한 유저 정보 출력");
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    @ApiOperation(value = "로그인", notes = "로그인 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginId", dataType = "String", value = "회원 로그인 아이디"),
            @ApiImplicitParam(name = "password", dataType = "String", value = "회원 비밀번호")
    })
    @ApiResponse(code=201, message="로그인 성공")
    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestParam("loginId") String loginId, @RequestParam("password") String password){
        log.info("로그인 시작");
        String token = userService.login(loginId, password);
        log.info("로그인 성공. 토큰 발급");
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @ApiOperation(value = "회원 조회", notes = "해당 회원 정보를 출력하는 API")
    @ApiImplicitParam(name = "Authentication", dataType = "String", value = "로그인 후 발급받은 토큰")
    @ApiResponse(code = 202, message = "회원 정보 출력")
    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestHeader("Authentication") String token){
        Long userNumber = Long.valueOf(jwtProvider.getUsername(token));
        log.info("발급받은 토큰으로 get 호출한 유저의 넘버:{}",userNumber);
        log.info("유저 정보 출력");
        return new ResponseEntity<>(userService.getUser(userNumber), HttpStatus.OK);
    }

    @ApiOperation(value = "회원 수정", notes = "해당 회원의 정보를 수정하는 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authentication", dataType = "String", value = "로그인 후 발급받은 토큰"),
            @ApiImplicitParam(name = "userUpdateDto", dataType = "UserUpdateDto", value = "수정할 내용이 담긴 dto")
    })
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestHeader("Authentication") String token, @RequestBody UserUpdateDto userUpdateDto){
        Long userNumber = Long.valueOf(jwtProvider.getUsername(token));
        log.info("발급받은 토큰으로 update 호출한 유저의 넘버:{}", userNumber);
        userService.update(userNumber, userUpdateDto);
        log.info("유저 넘버 출력");
        return new ResponseEntity<>(userNumber, HttpStatus.OK);
    }
}
