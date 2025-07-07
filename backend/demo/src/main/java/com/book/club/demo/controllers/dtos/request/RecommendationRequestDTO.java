package com.book.club.demo.controllers.dtos.request;

import com.book.club.demo.controllers.dtos.response.BookResponseDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public record RecommendationRequestDTO(
    @NotEmpty(message = "Recommendation must not be empty")
    BookRequestDTO book,
    @NotNull(message = "Reading number must not be null")
    Integer readingNumber) {

}
