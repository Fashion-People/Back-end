package com.example.capston.user.dto.User;

import lombok.Builder;
import lombok.Getter;
import com.example.capston.user.domain.UserEntity;

@Getter
public class UserResponseDto {
    private Long userNumber; //유저 넘버
    private String name; //이름
    private String loginId; //로그인 아이디
    private String email; //이메일
    private String style1; //취향1
    private String style2; //취향2
    private String style3; //취향3
    private String style4; //취향4

    @Builder
    public UserResponseDto(Long userNumber, String name, String loginId, String email, String style1, String style2, String style3, String style4){
        this.userNumber = userNumber;
        this.name = name;
        this.loginId = loginId;
        this.email = email;
        this.style1 = style1;
        this.style2 = style2;
        this.style3 = style3;
        this.style4 = style4;
    }
    public static UserResponseDto toDto(UserEntity userEntity){
        return UserResponseDto.builder()
                .userNumber(userEntity.getUserNumber())
                .name(userEntity.getName())
                .loginId(userEntity.getLoginId())
                .email(userEntity.getEmail())
                .style1(userEntity.getStyle().get(0))
                .style2(userEntity.getStyle().get(1))
                .style3(userEntity.getStyle().get(2))
                .style4(userEntity.getStyle().get(3))
                .build();
    }
}
