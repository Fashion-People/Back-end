package com.example.capston.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다"),
    CLOTHES_NOT_FOUND(HttpStatus.NOT_FOUND, "옷이 존재하지 않습니다"),
    LOGINID_NOT_FOUND(HttpStatus.UNAUTHORIZED, "로그인 아이디가 존재하지 않습니다"),
    USER_DUPLICATE_LOGINID(HttpStatus.BAD_REQUEST, "동일한 로그인 아이디가 존재합니다"),
    USER_DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "동일한 이메일이 존재합니다"),
    PASSWORD_NOT_MATCHED(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다"),
    UPDATE_NOT_PERMITTED(HttpStatus.UNAUTHORIZED, "수정 불가"),
    DELETE_NOT_PERMITTED(HttpStatus.FORBIDDEN, "삭제 불가"),
    INERNAL_SERVER_ERROR(HttpStatus.BAD_REQUEST, "서버 오류"),
    IMAGE_NOT_IDENTIFIED(HttpStatus.BAD_REQUEST, "이미지 분석 실패");

    private final HttpStatus status;
    private final String message;
}
