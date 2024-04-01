package com.example.capston.result.controller;

import com.example.capston.result.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ResultController {

    //인공지능 외부 api 테스트용
    @GetMapping("/test/get")
    public ResponseEntity<?> get(){
        List<AiRequestDto> aiRequestDtos = new ArrayList<>();
        aiRequestDtos.add(AiRequestDto.builder()
                .clothesNumber(0L)
                .clothesStyle("string")
                .clothesType("패딩")
                .tempNumber(2L)
                .build());

        return new ResponseEntity<>(aiRequestDtos, HttpStatus.OK);
    }
}
