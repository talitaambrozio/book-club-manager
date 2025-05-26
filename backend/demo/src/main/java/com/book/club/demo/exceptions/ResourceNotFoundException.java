package com.book.club.demo.exceptions;

import lombok.Getter;

import java.io.Serial;

import com.book.club.demo.exceptions.dtos.ErrorCauseDto;

@Getter
public class ResourceNotFoundException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    private String message;
    private ErrorCauseDto errorCause;

    public ResourceNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public ResourceNotFoundException(String message, ErrorCauseDto errorCause) {
        super();
        this.message = message;
        this.errorCause = errorCause;

    }
}
