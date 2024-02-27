package com.example.capston.user.dto.User;

import lombok.*;

@Getter
public class UserUpdateDto {
    private String loginId;
    private String userName;
    private String email;
    private String style1;
    private String style2;
    private String style3;
    private String style4;

    @Builder
    public UserUpdateDto(String userName, String loginId, String email,
                          String style1, String style2, String style3, String style4){
        this.userName = userName;
        this.loginId = loginId;
        this.email = email;
        this.style1 = style1;
        this.style2 = style2;
        this.style3 = style3;
        this.style4 = style4;
    }
}
