package com.example.capston.user.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.*;

@Entity
@Table(name = "user") //테이블 엔티티 생성
@Getter
@NoArgsConstructor
public class UserEntity implements UserDetails {
    @Id //기본키
    @Column(name="userNumber")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNumber;

    @Column(name = "userName")
    private String name;

    @Column(name = "login_Id")
    private String loginId;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "style_1")
    private String style1;

    @Column(name = "style_2")
    private String style2;

    @Column(name = "style_3")
    private String style3;

    @Column(name = "style_4")
    private String style4;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ClothesEntity> clothesEntities = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "user_role")
    private List<String> roles;

    @Builder
    public UserEntity(Long userNumber, String name, String loginId, String password, String email, String style1, String style2, String style3, String style4, List<String> roles){
        this.userNumber = userNumber;
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.style1 = style1;
        this.style2 = style2;
        this.style3 = style3;
        this.style4 = style4;
        this.roles = roles;
    }
    //스프링 시큐리티를 위한 설정
    //사용자에게 부여된 권한 목록 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
    //사용자의 고유 식별자인 유저 넘버 반환
    @Override
    public String getUsername(){
        return this.userNumber.toString();
    }

    // 사용자 계정이 만료되었는지 여부를 반환
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    // 사용자 계정이 잠겨있는지 여부를 반환
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    // 사용자의 인증 정보가 만료되었는지 여부를 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    // 사용자가 활성화되었는지 여부를 반환
    @Override
    public boolean isEnabled() {
        return true;
    }

    public void update(String name, String loginId, String email, String style1, String style2, String style3, String style4){
        this.name = name;
        this.loginId = loginId;
        this.email = email;
        this.style1 = style1;
        this.style2 = style2;
        this.style3 = style3;
        this.style4 = style4;
    }

    //사용자의 옷 종류 가져오기
}
