package com.example.capston.user.controller;

import com.example.capston.config.JwtProvider;
import com.example.capston.user.dto.Clothes.ClothesSaveDto;
import com.example.capston.user.dto.Clothes.ClothesUpdateDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.capston.user.service.ClothesService;

@Slf4j
@RestController
@RequestMapping("/clothes")
@RequiredArgsConstructor
public class ClothesController {

    private final ClothesService clothesService;
    private final JwtProvider jwtProvider;

    @ApiOperation(value = "옷 데이터 저장", notes = "옷 데이터 저장 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authentication", dataType = "String", value = "로그인 후 발급받은 토큰"),
            @ApiImplicitParam(name = "clothesSaveDto", dataType = "ClothesSaveDto", value = "저장할 옷 데이터")
    })
    @ApiResponse(code = 200, message = "옷 데이터 저장 성공")
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestHeader("Authentication") String token, @RequestBody ClothesSaveDto clothesSaveDto){
        log.info("옷 데이터 저장");
        Long userNumber = Long.valueOf(jwtProvider.getUsername(token));
        Long clothesNumber = clothesService.save(userNumber,clothesSaveDto);
        return new ResponseEntity<>(clothesNumber, HttpStatus.CREATED);
    }

    @ApiOperation(value = "옷 리스트 조회", notes = "옷 리스트 조회 API")
    @ApiImplicitParam(name = "loginId", dataType = "String", value = "로그인 아이디")
    @ApiResponse(code = 201, message = "옷 리스트 조회 성공")
    @GetMapping("/all/{loginId}")
    public ResponseEntity<?> getAllClothes(@PathVariable String loginId){
        log.info("옷 데이터 전체 조회 : {}", loginId);
        return new ResponseEntity<>(clothesService.getAllClothes(loginId), HttpStatus.OK);
    }

    @ApiOperation(value = "옷 데이터 조회", notes = "옷 데이터 조회 API")
    @ApiImplicitParam(name = "clothesNumber", dataType = "String", value = "옷 데이터 번호")
    @ApiResponse(code = 202, message = "옷 데이터 조회 성공")
    @GetMapping("/{clothesNumber}")
    public ResponseEntity<?> get(@PathVariable Long clothesNumber){
        log.info("옷 데이터 조회 : {}", clothesNumber);
        return new ResponseEntity<>(clothesService.getClothes(clothesNumber), HttpStatus.OK);
    }

    @ApiOperation(value = "옷 데이터 수정", notes = "옷 데이터 수정 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clothesNumber", dataType = "Long", value = "옷 데이터 번호"),
            @ApiImplicitParam(name = "Authentication", dataType = "String", value = "로그인 후 발급받은 토큰"),
            @ApiImplicitParam(name = "clothesUpdateDto", dataType = "ClothesUpdateDto", value = "수정할 옷 데이터")
    })
    @ApiResponse(code = 203, message = "옷 데이터 수정 성공")
    @PutMapping("/update/{clothesNumber}")
    public ResponseEntity<?> update(@PathVariable Long clothesNumber, @RequestHeader("Authentication") String token, @RequestBody ClothesUpdateDto clothesUpdateDto){
        log.info("옷 데이터 수정 : {}", clothesNumber);
        Long userNumber = Long.valueOf(jwtProvider.getUsername(token));
        clothesService.update(clothesNumber, clothesUpdateDto, userNumber);
        return new ResponseEntity<>(clothesService.getClothes(clothesNumber), HttpStatus.OK);
    }

    @ApiOperation(value = "옷 데이터 삭제", notes = "옷 데이터 삭제 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authentication", dataType = "String", value = "로그인 후 발급받은 토큰"),
            @ApiImplicitParam(name = "clothesNumber", dataType = "Long", value = "삭제할 옷 데이터 번호")
    })
    @ApiResponse(code = 204, message = "옷 데이터 삭제 성공")
    @DeleteMapping("/{clothesNumber}")
    public ResponseEntity<?> delete(@RequestHeader("Authentication") String token,@PathVariable Long clothesNumber){
        log.info("옷 데이터 삭제 : {}", clothesNumber);
        Long userNumber = Long.valueOf(jwtProvider.getUsername(token));
        clothesService.delete(clothesNumber, userNumber);
        return new ResponseEntity<>(clothesService, HttpStatus.NO_CONTENT);
    }
}
