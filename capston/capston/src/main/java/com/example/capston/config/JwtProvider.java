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

    public String createToken(String loginId, List<String> roles){
        log.info("Access Token 생성 시작");
        Claims claims = Jwts.claims().setSubject(loginId);
        claims.put("roles", roles);
        Date now = new Date();

        log.info("Refresh Token 생성 시작");
        String token = Jwts.builder()
                .setClaims(claims) //정보 저장
                .setIssuedAt(now) //토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime()+exp)) //유효시간 설정
                .signWith(secretkey, SignatureAlgorithm.HS256) //사용할 암호화 알고리즘과 secret값 설정
                .compact();
        return token;
    }
    // 권한 확인을 위해 권한 정보 획득
    public Authentication getAuthentication(String accessToken){
        log.info("토큰 정보 획득 시작");
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(accessToken));
        log.info("토큰 정보 획득 완료 user:{}", userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String accessToken){
        log.info("회원 정보 추출");
        String info = Jwts.parserBuilder().setSigningKey(secretkey).build().parseClaimsJws(accessToken).getBody().getSubject();
        return info;
    }

    public String resolveToken(HttpServletRequest request){
        log.info("Request Header에서 토큰 추출");
        return request.getHeader("Authentication");
    }

    public boolean validateAccessToken(String accessToken){
        log.info("토큰 유효성 검증 시작");
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretkey).build().parseClaimsJws(accessToken);
            log.info("토큰 유효성 검증 완료");
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            log.info("토큰 예외 발생");
            return false;
        }
    }
}
