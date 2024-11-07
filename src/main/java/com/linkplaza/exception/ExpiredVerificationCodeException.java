package com.linkplaza.exception;

public class ExpiredVerificationCodeException extends RuntimeException {
    public ExpiredVerificationCodeException() {
    }

    public ExpiredVerificationCodeException(String message) {
        super(message);
    }
}
