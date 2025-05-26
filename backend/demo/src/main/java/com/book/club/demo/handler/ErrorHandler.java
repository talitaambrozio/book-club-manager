package com.book.club.demo.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.book.club.demo.exceptions.*;
import com.book.club.demo.exceptions.dtos.ApiErrorDto;
import com.book.club.demo.exceptions.dtos.ErrorCauseDto;

import java.util.List;


@RestControllerAdvice
public class ErrorHandler {

    public ResponseEntity<ApiErrorDto> checkReturnWithOrWithoutCause(ErrorCauseDto cause, String message, HttpStatus status) {
        if (cause == null) {
            ApiErrorDto apiError = new ApiErrorDto(message, status);
            return new ResponseEntity<>(apiError, new HttpHeaders(), status);
        }

        ApiErrorDto apiError = new ApiErrorDto(message, status, List.of(cause));
        return new ResponseEntity<>(apiError, new HttpHeaders(), status);
    }

    public ResponseEntity<ApiErrorDto> checkReturnWithOrWithoutCause(List<ErrorCauseDto> causes, String message, HttpStatus status) {
        ApiErrorDto apiError = new ApiErrorDto(message, status, causes);
        return new ResponseEntity<>(apiError, new HttpHeaders(), status);
    }

    @ExceptionHandler({ BadRequestException.class })
    public ResponseEntity<ApiErrorDto> badRequestExceptionError400(BadRequestException ex) {
        return checkReturnWithOrWithoutCause(ex.getErrorCause(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ ResourceNotFoundException.class })
    public ResponseEntity<ApiErrorDto> resourceNotFoundExceptionError404(ResourceNotFoundException ex) {
        return checkReturnWithOrWithoutCause(ex.getErrorCause(), ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ ConflictException.class })
    public ResponseEntity<ApiErrorDto> conflictExceptionError409(ConflictException ex) {
        return checkReturnWithOrWithoutCause(ex.getErrorCause(), ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ InternalServerErrorException.class })
    public ResponseEntity<ApiErrorDto> internalServerErrorExceptionError500(InternalServerErrorException ex) {
        return checkReturnWithOrWithoutCause(ex.getErrorCause(), ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
