package com.book.club.demo.controllers.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(
    @NotBlank(message = "Must not be blank")
    String username, 
    @NotBlank(message = "Must not be blank")
    String password) {
}
