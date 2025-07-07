package com.book.club.demo.controllers.dtos.response;

import java.util.UUID;

public record BookResponseDTO(
    UUID bookId,
    String title,
    String author
) {
}
