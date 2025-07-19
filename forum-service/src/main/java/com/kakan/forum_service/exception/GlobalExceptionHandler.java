package com.kakan.forum_service.exception;

import com.kakan.forum_service.dto.response.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PostNotFoundException.class)
    public ErrorResponseDto handlePostNotFound(PostNotFoundException ex) {
        return ErrorResponseDto.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(ReportNotFoundException.class)
    public ErrorResponseDto reportNotFoundException(ReportNotFoundException ex) {
        return ErrorResponseDto.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponseDto methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return ErrorResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(Objects.requireNonNull(exception.getFieldError()).getDefaultMessage())
                .build();
    }
}