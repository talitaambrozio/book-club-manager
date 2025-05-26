package com.book.club.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.club.demo.controllers.dtos.RecommendationDTO;
import com.book.club.demo.models.dtos.BookDTO;
import com.book.club.demo.services.RecommendationService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/v1/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping("")
    public ResponseEntity<String> saveBookRecommendation(@RequestBody RecommendationDTO recommendationDTO) {
        return new ResponseEntity<>(recommendationService.saveBookRecommendation(recommendationDTO.book(), recommendationDTO.readingNumber()), HttpStatus.CREATED);
    }

}
