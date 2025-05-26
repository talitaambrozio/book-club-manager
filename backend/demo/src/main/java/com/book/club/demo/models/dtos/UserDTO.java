package com.book.club.demo.models.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserDTO(

        @NotBlank(message = "Must not be blank")
         String username,
        @NotBlank(message = "Must not be blank")
        String password

) {
}
