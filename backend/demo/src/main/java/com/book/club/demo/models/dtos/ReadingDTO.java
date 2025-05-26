package com.book.club.demo.models.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public record ReadingDTO(
    @JsonProperty(access = Access.READ_ONLY)
    BookDTO book,
    @NotNull
    Integer readingNumber,
    @NotNull(message = "Start date must not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate startDate,   
    @NotNull(message = "End date must not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate endDate
) {

}
