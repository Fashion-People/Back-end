package com.example.test.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
@Getter
public class CustomUserDetails implements UserDetails {
    private final Member member;

    @Override

}
