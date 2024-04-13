package com.example.capston.user.service;

import com.example.capston.config.JwtProvider;
import com.example.capston.exception.ErrorCode;
import com.example.capston.exception.ImageErrorException;
import com.example.capston.result.dto.AiRequestDto;
import com.example.capston.result.dto.FigureDto;
import com.example.capston.result.service.ResultService;
import com.example.capston.user.domain.TempEntity;
import com.example.capston.user.dto.Temp.TempRequestDto;
import com.example.capston.user.dto.Temp.TempResponseDto;
import com.example.capston.user.repository.TempRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TempService {
    private final TempRepository tempRepository;
    private final WebClient webClient;
    private final ResultService resultService;
    private final JwtProvider jwtProvider;

    public Mono<?> callExternalApi(TempRequestDto tempRequestDto, String token){
        ObjectMapper objectMapper = new ObjectMapper();
        TempEntity tempEntity = tempRepository.save(requestDtoToEntity(tempRequestDto,token));
        TempResponseDto tempResponseDto = entityToResponseDto(tempEntity);

        try {
            // tempResponseDto 객체를 JSON 문자열로 변환
            String json = objectMapper.writeValueAsString(tempResponseDto);
            log.info("json : {}",json);

            // webClient를 사용하여 외부 API에 POST 요청 보내기
            return webClient.post()
                    .uri("/imageAnalysis/")
                    .bodyValue(json)
                    .retrieve()
                    .bodyToMono(String.class)
                    .flatMap(responseJson -> {
                        try {
                            if (responseJson.contains("Unidentified image error")){
                                log.info("오류 발생 : {}", responseJson.toString());
                                return Mono.error(new ImageErrorException(ErrorCode.IMAGE_NOT_IDENTIFIED));
                            }
                            else {
                                // JSON 문자열을 AiRequestDto 리스트로 변환
                                List<AiRequestDto> aiRequestDtoList = objectMapper.readValue(responseJson, new TypeReference<List<AiRequestDto>>() {
                                });
                                log.info("{}", aiRequestDtoList.get(0).getClothesType());
                                // AiRequestDto 리스트를 이용하여 결과 계산
                                List<FigureDto> list = resultService.fit_calculation(aiRequestDtoList, tempResponseDto.getTempNumber());
                                return Mono.just(list);
                            }
                        } catch (JsonProcessingException e) {
                            return Mono.error(e);
                        }
                    });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Mono.error(e);
        }
    }

    private TempEntity requestDtoToEntity(TempRequestDto tempRequestDto, String token){
        Long tempNumber = Long.valueOf(jwtProvider.getUsername(token));
        TempEntity tempEntity = TempEntity.builder()
                .tempNumber(tempNumber)
                .imageUrl(tempRequestDto.getImageUrl())
                .build();
        return tempEntity;
    }

    private TempResponseDto entityToResponseDto(TempEntity tempEntity){
        TempResponseDto tempResponseDto = TempResponseDto.builder()
                .tempNumber(tempEntity.getTempNumber())
                .imageUrl(tempEntity.getImageUrl())
                .build();
        return tempResponseDto;
    }
}

