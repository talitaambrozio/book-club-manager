package com.book.club.demo.exceptions;

import com.book.club.demo.exceptions.dtos.ErrorCauseDto;

import lombok.Getter;


@Getter
public class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String message;
    private ErrorCauseDto errorCause;

    public BadRequestException(String message) {
        super(message);
        this.message = message;
    }

    public BadRequestException(String message, ErrorCauseDto errorCause) {
        super(message);
        this.message = message;
        this.errorCause = errorCause;
    }



    
}
