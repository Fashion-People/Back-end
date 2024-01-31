package com.example.capston.user.dto.User;

import com.example.capston.user.domain.UserEntity;
import lombok.*;

@Getter
public class UserRequestDto { //회원 관련 정보를 필드로 정의
    private String userName; //이름
    private String loginId; //로그인 아이디
    private String password;
    private String email;
    private String style1;
    private String style2;
    private String style3;
    private String style4;

    @Builder
    public UserRequestDto(String userName, String loginId, String password,String email,
                          String style1, String style2, String style3, String style4){
        this.userName = userName;
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.style1 = style1;
        this.style2 = style2;
        this.style3 = style3;
        this.style4 = style4;
    }

    public static UserEntity toEntity(UserRequestDto userRequestDto){
        return UserEntity.builder()
                .userName(userRequestDto.getUserName())
                .loginId(userRequestDto.getLoginId())
                .password(userRequestDto.getPassword())
                .email(userRequestDto.getEmail())
                .style1(userRequestDto.getStyle1())
                .style2(userRequestDto.getStyle2())
                .style3(userRequestDto.getStyle3())
                .style4(userRequestDto.getStyle4())
                .build();
    }
}