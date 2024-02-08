package com.example.capston.user.service.Weather;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Slf4j
public class ApiService {
    //openweathermap에서 받은 api 키
    @Value("${openweathermap.key}")
    private String apiKey;

    public String getWeatherString(String latitude, String longitude){
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + apiKey;

        try{
            URL url = new URL(apiUrl); //URL 객체 생성
            //HTTP 연결 설정 및 HTTP 통신 수행
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader br;

            int responseCode = connection.getResponseCode();
            //HTTP 응답 코드 확인
            if(responseCode == 200){ //HTTP 응답 코드가 200이면
                //성공 응답 스트림 읽음
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }else{ //그렇지 않으면 오류 스트림 읽음
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            String inputLine; //문자열을 한 줄씩 읽어올 변수
            StringBuilder response = new StringBuilder(); //서버 응답을 저장할 객체 생성
            while((inputLine = br.readLine()) != null){
                response.append(inputLine); //서버의 응답 스트림에서 한 줄씩 읽어와 response에 추가
            }
            br.close();
            return  response.toString(); //모든 응답 내용을 담은 response를 문자열로 변환하여 반환
        } catch (Exception e){
            return "응답 실패";
        }
    }
}
