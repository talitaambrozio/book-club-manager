package com.book.club.demo.controllers.dtos.response;

import java.util.UUID;

public record RecommendationIdDTO(UUID bookId, UUID readingId) {
}
