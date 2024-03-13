package com.example.capston.outfit.controller;

import com.example.capston.outfit.service.OutfitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/outfit")
@RequiredArgsConstructor
public class OutfitController {
    private final OutfitService outfitService;

    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestParam String latitude, @RequestParam String longitude){
        return new ResponseEntity<>(outfitService.getOutfit(latitude, longitude), HttpStatus.OK);
    }
}
