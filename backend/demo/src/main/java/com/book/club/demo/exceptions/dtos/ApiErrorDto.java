package com.book.club.demo.exceptions.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorDto {

    private String message;
    private HttpStatus status;
    private List<ErrorCauseDto> causes;

    public ApiErrorDto(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public ApiErrorDto(String message, HttpStatus status, List<ErrorCauseDto> causes) {
        this.message = message;
        this.status = status;
        this.causes = causes;
    }
}
