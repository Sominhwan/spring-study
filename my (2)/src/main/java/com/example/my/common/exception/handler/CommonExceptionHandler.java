package com.example.my.common.exception.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.my.common.dto.ResDTO;
import com.example.my.common.exception.EntityNotFoundException;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public HttpEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        // key에 필드명 / value에 에러 메시지
        Map<String, String> errorMap = new HashMap<>();

        // 익셉션에서 에러 리스트를 찾아서 가공해줌
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(-1)
                        .message("잘못된 요청입니다.")
                        .data(errorMap)
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public HttpEntity<?> handleEntityNotFoundException(Exception exception) {

        String message = "잘못된 요청입니다.";

        if(exception.getMessage() != null && !exception.getMessage().equals("")){
            message = exception.getMessage();
        }

        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(-1)
                        .message(message)
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

}
