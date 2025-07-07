package com.book.club.demo.controllers.dtos.response;


import java.util.UUID;

public record UserResponseDTO(

    UUID userId,
    String username) {
}
