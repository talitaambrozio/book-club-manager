package com.book.club.demo.controllers.dtos;

import com.book.club.demo.models.dtos.BookDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RecommendationDTO(
    @NotEmpty(message = "Recommendation must not be empty")
    BookDTO book,
    @NotNull(message = "Reading number must not be null")
    Integer readingNumber) {


}
