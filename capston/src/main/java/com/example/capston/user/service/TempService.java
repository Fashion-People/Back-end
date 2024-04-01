package com.example.capston.user.service;

import com.example.capston.config.JwtProvider;
import com.example.capston.user.domain.TempEntity;
import com.example.capston.user.dto.Temp.TempRequestDto;
import com.example.capston.user.dto.Temp.TempResponseDto;
import com.example.capston.user.repository.TempRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class TempService {
    private final TempRepository tempRepository;
    private final WebClient webClient;
    private final JwtProvider jwtProvider;

    public Mono<?> callExternalApi(TempRequestDto tempRequestDto, String token){
        log.info("분석할 데이터 저장");
        TempEntity tempEntity = tempRepository.save(requestDtoToEntity(tempRequestDto,token));
        TempResponseDto tempResponseDto = entityToResponseDto(tempEntity);
        log.info("외부 api 호출");
        return webClient.post()
                .uri("/test/ai")
                .bodyValue(tempResponseDto)
                .retrieve()
                .bodyToMono(TempResponseDto.class);
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

