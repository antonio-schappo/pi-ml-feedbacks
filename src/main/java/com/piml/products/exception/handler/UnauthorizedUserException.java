package com.piml.products.exception.handler;

public class UnauthorizedUserException extends RuntimeException {
    public UnauthorizedUserException(String errorMessage) {
        super(errorMessage);
    }
}
