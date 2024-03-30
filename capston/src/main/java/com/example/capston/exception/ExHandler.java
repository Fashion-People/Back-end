package com.example.capston.exception;

import com.example.capston.user.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class ExHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> ConstraintViolationException(ConstraintViolationException ex){
        log.error("유효성 검사 예외 발생 : {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> NotFoundException(NotFoundException ex){
        log.error("존재하지 않는 값 예외 발생 : {}", ex.getErrorCode().getMessage());
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ex.getErrorCode().getMessage());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<?> DuplicateException(DuplicateException ex){
        log.error("중복 값 예외 발생 : {}", ex.getErrorCode().getMessage());
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ex.getErrorCode().getMessage());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> UnauthorizedException(UnauthorizedException ex){
        log.error("로그인 실패 : {}", ex.getErrorCode().getMessage());
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ex.getErrorCode().getMessage());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotPermittedException.class)
    public ResponseEntity<?> NotPermittedException(NotPermittedException ex){
        log.error("접근 불가 : {}", ex.getErrorCode().getMessage());
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ex.getErrorCode().getMessage());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }
}
