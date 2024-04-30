package com.example.capston.user.controller;

import com.example.capston.config.JwtProvider;
import com.example.capston.exception.ErrorCode;
import com.example.capston.exception.ImageErrorException;
import com.example.capston.exception.InternalServerErrorException;
import com.example.capston.outfit.service.OutfitService;
import com.example.capston.result.domain.FigureEntity;
import com.example.capston.result.dto.OutfitResultDto;
import com.example.capston.result.dto.UserResultDto;
import com.example.capston.result.service.ResultService;
import com.example.capston.result.service.UserResultService;
import com.example.capston.user.domain.SituationEntity;
import com.example.capston.user.domain.UserEntity;
import com.example.capston.user.dto.Temp.TempRequestDto;
import com.example.capston.user.repository.UserRepository;
import com.example.capston.user.service.SituationService;
import com.example.capston.user.service.TempService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    private final SituationService situationService;
    private final UserRepository userRepository;

    @ApiOperation(value = "옷 적합도 분석", notes = "옷 적합도 분석 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authentication", dataType = "String", value = "로그인 후 발급받은 토큰"),
            @ApiImplicitParam(name = "tempRequestDto", dataType = "TempRequestDto", value = "분석할 데이터")
    })
    @ApiResponse(code = 200, message = "적합도 분석 성공")
    @PostMapping("/analysis")
    @ResponseBody
    public ResponseEntity<?> analysis(@RequestHeader("Authentication") String token, @RequestBody TempRequestDto tempRequestDto){

        Long userNumber = Long.valueOf(jwtProvider.getUsername(token));
        String situation = tempRequestDto.getSituation();
        SituationEntity situationEntity = situationService.getSituationEntity(situation);
        Optional<UserEntity> userEntity = userRepository.findById(userNumber);

            List<String> userStyle = userEntity.get().getStyle();
            String situationStyle = situationEntity.getStyle();
            int index = -1;
            for (int i = 0; i < userStyle.size(); i++) {
                if(situationStyle.equals(userStyle.get(i))){
                    index = i;
                    break;
                }
            }if(index != -1){
                log.info("원래 첫번째 스타일 :{}, 상황에 적합한 스타일: {}", userStyle.get(0),userStyle.get(index));
                Collections.swap(userStyle,0,index);
                log.info("바뀐 첫번째 스타일 : {}, 이동된 스타일 : {}", userStyle.get(0),userStyle.get(index));

        }

        UserResultDto userResultDto = userResultService.getResult(userNumber);
        userResultDto = UserResultDto.builder()
                .userNumber(userResultDto.getUserNumber())
                .style1(userStyle.get(0))
                .style2(userStyle.get(1))
                .style3(userStyle.get(2))
                .style4(userStyle.get(3))
                .build();
        OutfitResultDto outfitResultDto = outfitService.getOutfit(tempRequestDto.getLatitude(), tempRequestDto.getLongitude());
        log.info("현재 날씨에 맞는 기온별 옷차림 데이터와 사용자의 취향 4개 저장");
        resultService.save(userResultDto, outfitResultDto);

        List<FigureEntity> list = new ArrayList<>();
        log.info("인공지능으로 데이터 보내기");

        return tempService.callExternalApi(tempRequestDto,token).flatMap(
                result-> {
                    Long tempNumber = Long.valueOf(jwtProvider.getUsername(token));
                    return Mono.just(new ResponseEntity<>(resultService.get(tempNumber), HttpStatus.OK));
                }).onErrorResume(
                error -> {
                    if (error instanceof ImageErrorException) { //이미지 분석 오류
                        return Mono.error(error);
                    } else {
                        return Mono.error(new InternalServerErrorException(ErrorCode.INERNAL_SERVER_ERROR));
                    }
                }).block();
    }

}
