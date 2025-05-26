package com.book.club.demo.exceptions.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorCauseDto {
    private String field;
    private String value;

    public ErrorCauseDto() {}

    public ErrorCauseDto(String field, String value) {
        this.field = field;
        this.value = value;
    }
}
