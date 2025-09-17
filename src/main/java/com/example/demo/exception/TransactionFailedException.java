package com.example.demo.exception;

public class TransactionFailedException extends RuntimeException {
    public TransactionFailedException(String msg, Throwable cause) { super(msg, cause); }
}