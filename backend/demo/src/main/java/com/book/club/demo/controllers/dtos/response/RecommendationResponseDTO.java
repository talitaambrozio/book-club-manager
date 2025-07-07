package com.book.club.demo.controllers.dtos.response;


import java.util.UUID;

public record RecommendationResponseDTO(
    UUID recommendationId,
    BookResponseDTO book,
    Integer readingNumber) {

}
