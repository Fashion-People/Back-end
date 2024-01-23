package com.example.capston.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.*;

@Component
@Slf4j
public class JwtProvider {

    private final UserDetailsService userDetailsService;
    private Key secretkey;
    private final long exp = 1000L * 60 * 60; //1시간 동안 토큰 유효

    @Autowired
    public JwtProvider(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
        this.secretkey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }
    // Access Token 생성
    public String createToken(String loginId, List<String> roles){
        Claims claims = Jwts.claims().setSubject(loginId);
        claims.put("roles", roles);
        Date now = new Date();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+exp))
                .signWith(secretkey, SignatureAlgorithm.HS256)
                .compact();
        return token;
    }
    // 권한 확인을 위해 권한 정보 획득
    public Authentication getAuthentication(String accessToken){
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(accessToken));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    //토큰에 담겨있는 유저의 로그인 아이디 획득
    public String getUsername(String accessToken){
        String info = Jwts.parserBuilder().setSigningKey(secretkey).build().parseClaimsJws(accessToken).getBody().getSubject();
        return info;
    }

    // Authorization Header를 통한 인증
    public String resolveToken(HttpServletRequest request){
        return request.getHeader("Authorization");
    }
    // Access Token 검증
    public boolean validateAccessToken(String accessToken){
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretkey).build().parseClaimsJws(accessToken);
            // 만료 시 false 반환
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
