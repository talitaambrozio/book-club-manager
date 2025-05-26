package com.book.club.demo.exceptions;


import com.book.club.demo.exceptions.dtos.ErrorCauseDto;

import lombok.Getter;

@Getter
public class InternalServerErrorException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private String message;
    private ErrorCauseDto errorCause;

    public InternalServerErrorException(String message) {
        super(message);
        this.message = message;
    }

    public InternalServerErrorException(String message, ErrorCauseDto errorCause) {
        super();
        this.message = message;
        this.errorCause = errorCause;
    }
}
