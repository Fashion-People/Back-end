package com.example.capston.user.controller;
import com.example.capston.user.service.Weather.WeatherService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
@Api(tags = "날씨 관련 API")
public class WeatherController {
    private final WeatherService weatherService;

    @ApiOperation(value = "저장된 날씨 데이터의 상태 가져오기", notes = "날씨 데이터 중 상태 출력 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "latitude", dataType = "String", value = "위도 값"),
            @ApiImplicitParam(name = "longitude", dataType = "String", value = "경도 값")
    })
    @ApiResponse(code=201, message="상태 출력 성공")
    @GetMapping("/get/condition")
    String get(@RequestParam String latitude, @RequestParam String longitude){
        String condition = weatherService.getCondition(latitude,longitude);
        if(condition == null){
            weatherService.insert(latitude,longitude);
            condition = weatherService.getCondition(latitude,longitude);
        }
        return condition;
    }
}
