package com.example.capston.user.service.Weather;

import com.example.capston.user.domain.WeatherEntity;
import com.example.capston.user.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final ApiService apiService;

    @Transactional
    public void insert(String latitude, String longitude, Long userNumber){ //날씨 저장
        WeatherEntity weatherDate = getDateWeather(latitude, longitude);
        weatherDate.setUserNumber(userNumber);
        weatherRepository.save(weatherDate);
    }

    @Transactional
    public void saveWeatherDate(String latitude,String  longitude){
        log.info("openweathermap api에서 가져온 정보로 날씨 데이터 저장");
        weatherRepository.save(getWeatherFromApi(latitude, longitude));
    }

    @Transactional(readOnly = true)
    public String getCondition(String latitude, String longitude){ //상태 가져오는 함수-위도,경도 사용
        LocalDate today = LocalDate.now();
        if(weatherRepository.existsByDateAndLatitudeAndLongitude(today,latitude,longitude)){
            WeatherEntity weatherEntity = weatherRepository.getByDateAndLatitudeAndLongitude(today,latitude,longitude);
            return weatherEntity.getWeather();
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Double getWeather(Long userNumber) { //날씨 가져오기 - userNumber 사용
        LocalDate today = LocalDate.now(); //현재 날짜 가져오기
        if(weatherRepository.existsByDateAndUserNumber(today,userNumber)) {
            List<WeatherEntity> weatherEntities =  weatherRepository.findByUserNumberAndDateOrderByWeatherNumber(userNumber, today);
            return weatherEntities.get(0).getTemperature();
        }
        return null;
    }

    private WeatherEntity getDateWeather(String latitude, String  longitude) {
        LocalDate today = LocalDate.now(); //현재 날짜 가져오기
        log.info("DB에서 date, latitude, longitude를 기반으로 날씨 정보 가져오기");
        List<WeatherEntity> dateWeatherListFromDB = weatherRepository.findByDateAndLatitudeAndLongitude(today, latitude,longitude);
        if (dateWeatherListFromDB.isEmpty()) { //해당 날짜에 대한 날씨 정보가 없으면
            return getWeatherFromApi(latitude, longitude); //새로운 API를 호출해서 날씨 정보 가져오기
        } else {
            return dateWeatherListFromDB.get(0);
        }
    }

    private WeatherEntity getWeatherFromApi(String latitude, String longitude){
        log.info("openWeatherMap에서 날씨 데이터 가져오기");
        String weatherData = apiService.getWeatherString(latitude, longitude);
        Map<String, Object> parsedWeather = parseWeather(weatherData);

        WeatherEntity weather = new WeatherEntity();
        weather.setDate(LocalDate.now());
        weather.setLatitude(latitude);
        log.info("위도 값 : {}",latitude);
        weather.setLongitude(longitude);
        log.info("경도 값 : {}",longitude);
        weather.setWeather(parsedWeather.get("main").toString());
        weather.setIcon(parsedWeather.get("icon").toString());
        weather.setTemperature(Math.round((((Double) parsedWeather.get("temp"))-273.0))*10/10.0);
        return weather;
    }

    private Map<String, Object> parseWeather(String jsonString) {
        log.info("JSON 데이터 파싱");
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;
        try{
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
        }catch(ParseException e){
            throw new RuntimeException(e);
        }

        Map<String, Object> resultMap = new HashMap<>();
        log.info("위도, 경도 값 파싱");
        JSONObject locationData = (JSONObject) jsonObject.get("coord");
        resultMap.put("lat", locationData.get("lat"));
        resultMap.put("lon", locationData.get("lon"));

        log.info("온도 값 파싱");
        JSONObject mainData = (JSONObject) jsonObject.get("main");
        resultMap.put("temp", mainData.get("temp"));

        log.info("날씨 값 파싱");
        JSONArray weatherArray = (JSONArray) jsonObject.get("weather");
        JSONObject weatherData = (JSONObject) weatherArray.get(0);
        resultMap.put("main", weatherData.get("main"));
        resultMap.put("icon", weatherData.get("icon"));

        return resultMap;
    }

}
