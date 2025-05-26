package com.book.club.demo.models.dtos;

import jakarta.validation.constraints.NotBlank;

public record BookDTO(
    @NotBlank(message = "Must not be blank")
    String title,
    String author
) {
}
