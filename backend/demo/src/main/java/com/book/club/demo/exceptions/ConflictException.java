package com.book.club.demo.exceptions;

import com.book.club.demo.exceptions.dtos.ErrorCauseDto;

import lombok.Getter;

@Getter
public class ConflictException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String message;
    private ErrorCauseDto errorCause;

    public ConflictException(String message) {
        super(message);
        this.message = message;
    }

    public ConflictException(String message, ErrorCauseDto errorCause) {
        super();
        this.message = message;
        this.errorCause = errorCause;
    }
}
