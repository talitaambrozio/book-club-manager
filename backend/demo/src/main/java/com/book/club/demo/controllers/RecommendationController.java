package com.book.club.demo.controllers;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import com.book.club.demo.controllers.dtos.request.BookRequestDTO;
import com.book.club.demo.controllers.dtos.request.RecommendationRequestDTO;
import com.book.club.demo.controllers.dtos.response.BookResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import com.book.club.demo.controllers.dtos.response.RecommendationResponseDTO;
import com.book.club.demo.services.RecommendationService;

import jakarta.websocket.server.PathParam;


@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/v1/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping("")
    public ResponseEntity<BookResponseDTO> saveBookRecommendation(@RequestBody RecommendationRequestDTO recommendationDTO) {
        BookResponseDTO response = recommendationService.saveBookRecommendation(recommendationDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{recommendationId}")
                .buildAndExpand(response.bookId())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("{recommendationId}")
    public ResponseEntity<RecommendationResponseDTO> getRecommendationById(@PathVariable("recommendationId") UUID recommendationId) {
        return new ResponseEntity<>(recommendationService.getRecommendationById(recommendationId), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<RecommendationResponseDTO>> getAllRecommendations() {
        return new ResponseEntity<>(recommendationService.getAllRecommendations(), HttpStatus.OK);
    }
    
    

}
