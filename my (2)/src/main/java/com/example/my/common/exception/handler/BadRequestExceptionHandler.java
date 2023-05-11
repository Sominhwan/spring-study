package com.example.my.common.exception.handler;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.my.common.dto.ResDTO;
import com.example.my.common.exception.BadRequestException;

@RestControllerAdvice
public class BadRequestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public HttpEntity<?> handleBadRequestException(BadRequestException exception) {
        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(-1)
                        .message(exception.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }
}
