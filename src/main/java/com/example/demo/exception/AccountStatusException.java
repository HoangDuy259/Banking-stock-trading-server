package com.example.demo.exception;

public class AccountStatusException extends RuntimeException{
    public AccountStatusException(String message) {
        super(message);
    }
}
