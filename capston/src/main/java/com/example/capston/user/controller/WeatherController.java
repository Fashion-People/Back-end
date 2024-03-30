package com.example.capston.user.controller;

import com.example.capston.user.dto.Weather.WeatherRequestDto;
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

    @ApiOperation(value = "입력한 위도와 경도에 해당하는 날씨 데이터 저장", notes = "해당 날씨 데이터 저장 API")
    @ApiResponse(code=200, message="날씨 데이터 저장 성공")
    @PostMapping("/save")
    public String insert(@RequestBody WeatherRequestDto weatherRequestDto){
        log.info("날씨 데이터 저장");
        weatherService.insert(weatherRequestDto.getLatitude(), weatherRequestDto.getLongitude());
        return "날씨 데이터 저장 성공";
    }

    @ApiOperation(value = "저장된 날씨 데이터의 온도 값 가져오기", notes = "날씨 데이터 중 온도 출력 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "latitude", dataType = "String", value = "위도 값"),
            @ApiImplicitParam(name = "longitude", dataType = "String", value = "경도 값")
    })
    @ApiResponse(code=201, message="온도 값 출력 성공")
    @GetMapping("/get/temperature")
    Double getDate(@RequestParam String latitude, @RequestParam String longitude){
        log.info("해당 날씨 데이터의 온도 값 출력");
        return weatherService.getWeather(latitude, longitude);
    }

    @ApiOperation(value = "저장된 날씨 데이터의 날씨 상태 가져오기", notes = "날씨 데이터 중 날씨 상태 출력 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "latitude", dataType = "String", value = "위도 값"),
            @ApiImplicitParam(name = "longitude", dataType = "String", value = "경도 값")
    })
    @ApiResponse(code=202, message="날씨 상태 출력 성공")
    @GetMapping("/get/condition")
    String getWeather(@RequestParam String latitude, @RequestParam String longitude){
        log.info("해당 날씨 데이터의 날씨 상태 출력");
        return weatherService.getCondition(latitude, longitude);
    }
}
