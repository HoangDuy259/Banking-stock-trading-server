package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {
    private final HttpStatus status;
    private final String code;

    public AppException(String message, HttpStatus status, String code) {
        super(message);
        this.status = status;
        this.code = code;
    }

    public static class UserNotFound extends AppException {
        public UserNotFound(String message) {
            super(message, HttpStatus.NOT_FOUND, "USER_NOT_FOUND");
        }
    }

    public static class InvalidPassword extends AppException {
        public InvalidPassword(String message) {
            super(message, HttpStatus.UNAUTHORIZED, "INVALID_PASSWORD");
        }
    }

    public static class UserAlreadyExists extends AppException {
        public UserAlreadyExists(String message) {
            super(message, HttpStatus.CONFLICT, "USER_ALREADY_EXISTS");
        }
    }

    public static class InvalidToken extends AppException {
        public InvalidToken(String message) {
            super(message, HttpStatus.UNAUTHORIZED, "INVALID_TOKEN");
        }
    }

    public static class PasswordMismatch extends AppException {
        public PasswordMismatch(String message) {
            super(message, HttpStatus.BAD_REQUEST, "PASSWORD_MISMATCH");
        }
    }

    public static class TokenExpired extends AppException {
        public TokenExpired(String message) {
            super(message, HttpStatus.UNAUTHORIZED, "TOKEN_EXPIRED");
        }
    }
}