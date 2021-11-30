package com.springsql.spring.mssql.api.demo.controller;

import com.springsql.spring.mssql.api.demo.dto.ExceptionResponse;
import com.springsql.spring.mssql.api.demo.exceptions.DoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> anyRuntimeException(RuntimeException e) {
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({IllegalArgumentException.class, DoesNotExistException.class, IllegalStateException.class})
    public ResponseEntity<ExceptionResponse> badRequestException(Exception e) {
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> anyException(Exception e) {
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
