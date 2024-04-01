package com.example.capston.result.service;

import com.example.capston.outfit.service.OutfitService;
import com.example.capston.result.domain.FigureEntity;
import com.example.capston.result.domain.ResultEntity;
import com.example.capston.result.dto.*;
import com.example.capston.result.repository.FigureRepository;
import com.example.capston.result.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResultService {
    private final ResultRepository resultRepository;
    private final FigureRepository figureRepository;
    private final WebClient webClient;
    private final OutfitService outfitService;
    private Long aiId = 0L;

    @Transactional
    public ResultDto save(UserResultDto userResultDto, OutfitResultDto outfitResultDto){
        ResultEntity resultEntity = ResultEntity.builder()
                .clothingTypes(outfitResultDto.getClothingTypes())
                .style1(userResultDto.getStyle1())
                .style2(userResultDto.getStyle2())
                .style3(userResultDto.getStyle3())
                .style4(userResultDto.getStyle4())
                .build();
        resultEntity = resultRepository.save(resultEntity);
        ResultDto resultDto = ResultDto.toDto(resultEntity);
        return resultDto;
    }

    //tempNumber에 해당하는 적합도 결과 리스트 반환
    public FigureResponseDto get(Long tempNumber) {
        FigureEntity figure = figureRepository.findByTempNumberOrderByIdDesc(tempNumber).get(0);

        FigureResponseDto figureResponseDto = FigureResponseDto.builder()
                .figure(figure.getFigure())
                .message(figure.getMessage())
                .clothesNumber(figure.getClothesNumber())
                .tempNumber(figure.getTempNumber())
                .aiId(figure.getAiId())
                .build();
        return figureResponseDto;
    }

    //외부 api 호출
    public Mono<List<AiRequestDto>> callExternalApi(){
        Flux<AiRequestDto> aiRequestDtoFlux =  webClient.get()
                .uri("/test/get")
                .retrieve()
                .bodyToFlux(AiRequestDto.class);
        return aiRequestDtoFlux.collectList();
    }

    public Mono<List<FigureDto>> responseExternalApi(Long tempNumber){
        return callExternalApi()
                .flatMap(aiRequestDtoList -> {
                    List<FigureDto> list = fit_calculation(aiRequestDtoList, tempNumber);
                    return Mono.just(list);
                });
    }

    //적합도 계산 함수
    public List<FigureDto> fit_calculation(List<AiRequestDto> aiRequestDtos, Long tempNumber){
        ResultEntity resultEntity = resultRepository.findFirstByOrderByResultNumberDesc();
        Long resultNumber = resultEntity.getResultNumber();

        List<FigureDto> result = new ArrayList<>();

        if(aiRequestDtos.size() == 1){
            AiRequestDto requestDto = aiRequestDtos.get(0);
            result.add(singleAiResponse(requestDto,resultNumber, tempNumber));
        }
        else if (aiRequestDtos.size() >= 2){
            result = multipleAiResponse(aiRequestDtos, resultNumber,tempNumber);
        }
        //리스트에 담긴 figureResponseDto를 entity로 저장
        for(FigureDto figureDto : result){
            FigureEntity figure = FigureEntity.builder()
                    .clothesNumber(figureDto.getClothesNumber())
                    .message(figureDto.getMessage())
                    .figure(figureDto.getFigure())
                    .tempNumber(figureDto.getTempNumber())
                    .aiId(figureDto.getAiId())
                    .build();
            figureRepository.save(figure);
        }
        return result;
    }

    public FigureDto singleAiResponse(AiRequestDto aiRequestDto, Long resultNumber,Long tempNumber){
        log.info("올린 이미지가 1개인 경우 실행");
        int figure = 10;
        String res = "";
        FigureDto figureDto;
        if(figureRepository.existsByTempNumber(tempNumber)) aiId +=1L;

        ResultEntity resultEntity = resultRepository.findById(resultNumber).orElseThrow();
        ResultDto resultDto = ResultDto.toDto(resultEntity);

        for(String type : resultDto.getClothingTypes()){
            if(aiRequestDto.getClothesType().equals(type)) {
                log.info("결과 : {}",figure);
                break;
            }
            else res = "불일치";
        }

        if (res.equals("불일치")) {
            Long number = outfitService.getNumber(aiRequestDto.getClothesType());
            figure = (int) (figure - Math.abs(outfitService.getNumber(resultEntity.getClothingTypes().get(0)) - number));
            log.info("결과 수치 : {}", figure);
        }

        figure *= 10;
        log.info("최종 결과 수치 : {}", figure);
        if(figure >= 60){
            figureDto = FigureDto.builder()
                    .clothesNumber(aiRequestDto.getClothesNumber())
                    .tempNumber(aiRequestDto.getTempNumber())
                    .figure(figure)
                    .message("추천합니다")
                    .build();
        }
        else if(figure==50){
            figureDto = FigureDto.builder()
                    .clothesNumber(aiRequestDto.getClothesNumber())
                    .tempNumber(aiRequestDto.getTempNumber())
                    .figure(figure)
                    .message("보통입니다")
                    .build();
        }
        else{
            figureDto = FigureDto.builder()
                    .clothesNumber(aiRequestDto.getClothesNumber())
                    .tempNumber(aiRequestDto.getTempNumber())
                    .figure(figure)
                    .message("비추천합니다")
                    .build();
        }
        return figureDto;
    }

    public List<FigureDto> multipleAiResponse(List<AiRequestDto> aiRequestDtos, Long resultNumber, Long tempNumber) {
        if(figureRepository.existsByTempNumber(tempNumber)) aiId +=1L;

        log.info("올린 이미지가 2개인 경우 실행");

        List<FigureDto> figureDtos = new ArrayList<>();
        FigureDto figureDto;

        ResultEntity resultEntity = resultRepository.findById(resultNumber).orElseThrow();
        ResultDto resultDto = ResultDto.toDto(resultEntity);

        for (AiRequestDto aiRequestDto : aiRequestDtos) {
            int figure = 10;
            String res = "";

            for (String type : resultDto.getClothingTypes()) {
                if (aiRequestDto.getClothesType().equals(type)) {
                    break;
                } else res = "불일치";
            }

            if (res.equals("불일치")) {
                Long number = outfitService.getNumber(aiRequestDto.getClothesType());
                figure = (int) (figure - Math.abs(outfitService.getNumber(resultEntity.getClothingTypes().get(0)) - number));
                log.info("결과 수치 : {}", figure);
            }

            if (aiRequestDto.getClothesStyle().equals(resultDto.getStyle1()) || aiRequestDto.getClothesStyle().equals(resultDto.getStyle2()) ||
                    aiRequestDto.getClothesStyle().equals(resultDto.getStyle3()) || aiRequestDto.getClothesStyle().equals(resultDto.getStyle4())) {
                figure = figure;
            } else {
                figure -= 1;
            }

            figure *= 10; //퍼센트로 변환
            if(figure >= 60){
                figureDto = FigureDto.builder()
                        .clothesNumber(aiRequestDto.getClothesNumber())
                        .tempNumber(aiRequestDto.getTempNumber())
                        .figure(figure)
                        .message("추천합니다")
                        .aiId(aiId)
                        .build();
            }
            else if(figure==50){
                figureDto = FigureDto.builder()
                        .clothesNumber(aiRequestDto.getClothesNumber())
                        .tempNumber(aiRequestDto.getTempNumber())
                        .figure(figure)
                        .message("보통입니다")
                        .aiId(aiId)
                        .build();
            }
            else{
                figureDto = FigureDto.builder()
                        .clothesNumber(aiRequestDto.getClothesNumber())
                        .tempNumber(aiRequestDto.getTempNumber())
                        .figure(figure)
                        .message("비추천합니다")
                        .aiId(aiId)
                        .build();
            }
            figureDtos.add(figureDto);
        }
        
        log.info("적합도 결과가 가장 높은 Dto가 있는 리스트와 합치기");
        List<FigureDto> figureMaxDto = findMaxDto(figureDtos);
        figureDtos.addAll(figureMaxDto);
        
        return figureDtos;
    }

    public List<FigureDto> findMaxDto(List<FigureDto> figureDtos){
        log.info("여러 개의 이미지 중 적합도 결과가 가장 높은 이미지 찾기(중복 포함)");
        List<FigureDto> figureMaxDtos = new ArrayList<>();
        int figureMax = figureDtos.get(0).getFigure(); //초기화

        for(FigureDto figureDto : figureDtos){
            if(figureDto.getFigure() > figureMax){
                figureMax = figureDto.getFigure();
                figureMaxDtos.clear();
            }
            if(figureDto.getFigure() == figureMax){
                figureDto = FigureDto.builder()
                        .clothesNumber(figureDto.getClothesNumber())
                        .tempNumber(figureDto.getTempNumber())
                        .figure(figureDto.getFigure())
                        .message("적합도 결과가 가장 높습니다")
                        .aiId(figureDto.getAiId())
                        .build();
                figureMaxDtos.add(figureDto);
            }
        }
        if(figureMaxDtos.size()>=2) {
            log.info("가장 적합도 결과가 높은 여러 개의 이미지 중 랜덤으로 추천하기");
            Random random = new Random();
            int index = random.nextInt(figureMaxDtos.size());
            FigureDto randomDto = figureMaxDtos.get(index);
            randomDto = FigureDto.builder()
                    .clothesNumber(randomDto.getClothesNumber())
                    .tempNumber(randomDto.getTempNumber())
                    .figure(randomDto.getFigure())
                    .message("랜덤으로 추천한 이미지입니다")
                    .aiId(randomDto.getAiId())
                    .build();
            figureMaxDtos.add(randomDto);
        }
        return figureMaxDtos;
    }
}
