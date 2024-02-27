package com.example.capston.user.service.Weather;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchedulerService {
    //기본 위도, 경도 값
    @Value("${default.latitude}")
    private String  defaultLatitude;
    @Value("${default.longitude}")
    private String  defaultLongitude;

    private final WeatherService weatherService;

    //매일 새벽 1시에 실행되도록 설정
   @Scheduled(cron = "0 0 1 * * *")
    public void saveWeatherDate(){
        weatherService.saveWeatherDate(defaultLatitude, defaultLongitude);
   }
}
