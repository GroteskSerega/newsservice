package com.news.newsservice.exception;

public class OperationUnauthorizedException extends RuntimeException {
    public OperationUnauthorizedException(String message) {
        super(message);
    }
}
