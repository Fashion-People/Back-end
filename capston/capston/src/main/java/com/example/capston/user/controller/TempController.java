package com.example.capston.user.controller;

import com.example.capston.user.dto.Temp.TempRequestDto;
import com.example.capston.user.service.TempService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/temp")
@RequiredArgsConstructor
public class TempController {
    private final TempService tempService;

    @ApiOperation(value = "옷 데이터 임시 저장", notes = "데이터 임시 저장 API")
    @ApiImplicitParam(name = "tempRequestDto", dataType = "TempRequestDto", value = "임시로 저장할 데이터")
    @ApiResponse(code = 200, message = "데이터 임시 저장 성공")
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody TempRequestDto tempRequestDto){
        log.info("데이터 임시 저장");
        Long tempNumber = tempService.tempSave(tempRequestDto);
        return new ResponseEntity<>(tempNumber, HttpStatus.OK);
    }

    @ApiOperation(value = "임시로 저장된 데이터 조회", notes = "임시로 저장된 데이터 조회 API")
    @ApiImplicitParam(name = "tempNumber", dataType = "Long", value = "임시 데이터 번호")
    @ApiResponse(code = 201, message = "임시로 저장된 데이터 조회 성공")
    @GetMapping("/{tempNumber}")
    public ResponseEntity<?> getImage(@PathVariable Long tempNumber){
        log.info("임시로 저장한 데이터 전체 조회 : {}", tempNumber);
        return new ResponseEntity<>(tempService.getImages(tempNumber), HttpStatus.OK);
    }
}
