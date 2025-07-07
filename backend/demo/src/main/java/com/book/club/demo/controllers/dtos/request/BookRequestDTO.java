package com.book.club.demo.controllers.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record BookRequestDTO(
    @NotBlank(message = "Must not be blank")
    String title,
    String author
) {
}
