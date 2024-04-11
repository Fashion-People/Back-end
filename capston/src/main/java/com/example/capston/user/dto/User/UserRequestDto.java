package com.example.capston.user.dto.User;

import com.example.capston.user.domain.UserEntity;
import lombok.*;

import java.util.Arrays;
import java.util.List;

@Getter
public class UserRequestDto { //회원 관련 정보를 필드로 정의
    private String name; //이름
    private String loginId; //로그인 아이디
    private String password;
    private String email;
    private String style1;
    private String style2;
    private String style3;
    private String style4;

    @Builder
    public UserRequestDto(String name, String loginId, String password,String email,
                          String style1, String style2, String style3, String style4){
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.style1 = style1;
        this.style2 = style2;
        this.style3 = style3;
        this.style4 = style4;
    }

    public static UserEntity toEntity(UserRequestDto userRequestDto){
        List<String> styles = Arrays.asList(
                userRequestDto.getStyle1(),
                userRequestDto.getStyle2(),
                userRequestDto.getStyle3(),
                userRequestDto.getStyle4()
        );
        return UserEntity.builder()
                .name(userRequestDto.getName())
                .loginId(userRequestDto.getLoginId())
                .password(userRequestDto.getPassword())
                .email(userRequestDto.getEmail())
                .style(styles)
                .build();
    }
}