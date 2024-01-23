package com.example.capston;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class) //main 메서드가 포함된 클래스를 나타내는 어노테이션
public class CapstonApplication {
    //SpringApplication.run()을 호출하여 스프링부트 애플리케이션 시작
    public static void main(String[] args){
        SpringApplication.run(CapstonApplication.class, args);
    }
}
