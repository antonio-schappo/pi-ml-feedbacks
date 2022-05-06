package com.piml.products.exception;

public class UnauthorizedUserException extends RuntimeException {
    public UnauthorizedUserException(String errorMessage) {
        super(errorMessage);
    }
}
