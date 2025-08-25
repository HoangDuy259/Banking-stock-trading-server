package com.example.demo.exception;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Data
    public static class ErrorResponse {
        private final int status;
        private final String code;
        private final String message;
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(AppException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getStatus().value(),
                ex.getCode(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }
}