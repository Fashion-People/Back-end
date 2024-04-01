package com.example.capston.user.controller;

import com.example.capston.config.JwtProvider;
import com.example.capston.outfit.service.OutfitService;
import com.example.capston.result.domain.FigureEntity;
import com.example.capston.result.dto.OutfitResultDto;
import com.example.capston.result.dto.UserResultDto;
import com.example.capston.result.service.ResultService;
import com.example.capston.result.service.UserResultService;
import com.example.capston.user.dto.Temp.TempRequestDto;
import com.example.capston.user.dto.Temp.TempResponseDto;
import com.example.capston.user.service.TempService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "분석 API")
public class TempController {
    private final TempService tempService;
    private final ResultService resultService;
    private final UserResultService userResultService;
    private final OutfitService outfitService;
    private final JwtProvider jwtProvider;

    @ApiOperation(value = "옷 적합도 분석", notes = "옷 적합도 분석 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authentication", dataType = "String", value = "로그인 후 발급받은 토큰"),
            @ApiImplicitParam(name = "tempRequestDto", dataType = "TempRequestDto", value = "분석할 데이터")
    })
    @ApiResponse(code = 200, message = "적합도 분석 성공")
    @PostMapping("/analysis")
    public ResponseEntity<?> analysis(@RequestHeader("Authentication") String token, @RequestBody TempRequestDto tempRequestDto){

        Long userNumber = Long.valueOf(jwtProvider.getUsername(token));
        UserResultDto userResultDto = userResultService.getResult(userNumber);
        OutfitResultDto outfitResultDto = outfitService.getOutfit(tempRequestDto.getLatitude(), tempRequestDto.getLongitude());
        log.info("현재 날씨에 맞는 기온별 옷차림 데이터와 사용자의 취향 4개 저장");
        resultService.save(userResultDto, outfitResultDto);

        List<FigureEntity> list = new ArrayList<>();
        log.info("인공지능으로 데이터 보내기");

        Mono<?> resultMono = tempService.callExternalApi(tempRequestDto,token);

        return tempService.callExternalApi(tempRequestDto,token).flatMap(
                responseDto -> {
                    Long tempNumber = Long.valueOf(jwtProvider.getUsername(token));
                    log.info("temp_number : {}", tempNumber);
                    log.info("인공지능에서 데이터 받아서 적합도 계산하기");
                    resultService.responseExternalApi(tempNumber);
                    return resultService.responseExternalApi(tempNumber)
                            .flatMap(result -> Mono.just(new ResponseEntity<>(resultService.get(tempNumber), HttpStatus.OK)));
                }).onErrorResume(
                error -> {
                    Long tempNumber = Long.valueOf(jwtProvider.getUsername(token));
                    return Mono.just(new ResponseEntity<>(resultService.get(tempNumber), HttpStatus.INTERNAL_SERVER_ERROR));
                }).block();
    }

    @ApiOperation(value = "인공지능 외부 api 테스트용", notes = "테스트 API")
    @ApiImplicitParam(name = "tempResponseDto", dataType = "TempResponseDto", value = "전달할 데이터")
    @ApiResponse(code = 200, message = "적합도 분석 성공")
    @PostMapping("/test/ai")
    public ResponseEntity<?> get(@RequestBody TempResponseDto tempResponseDto){
        return new ResponseEntity<>(tempResponseDto, HttpStatus.OK);
    }

}
