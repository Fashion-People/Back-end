package com.example.test.member.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member") //테이블 엔티티 생성
public class Member {
    @Id //기본키
    @Column(name="userNumber")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userNumber;

    @Column(name = "userName")
    private String userName;

    @Column(name = "userId")
    private String userId;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "style")
    private String style;

    @Builder
    public Member(String userName, String userId, String password, String email, String style){
        this.userName = userName;
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.style = style;
    }

    public Member updateName(String userName){
        this.userName = userName;
        return this;
    }
    public Member updateId(String userId){
        this.userId = userId;
        return this;
    }
    public Member updatePassword(String password){
        this.password = password;
        return this;
    }
    public Member updateEmail(String email){
        this.email = email;
        return this;
    }
    public Member updateStyle(String style){
        this.style = style;
        return this;
    }

}
