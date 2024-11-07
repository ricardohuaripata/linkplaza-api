package com.linkplaza.exception;

public class InvalidAccountVerificationException extends RuntimeException {
    public InvalidAccountVerificationException() {
    }

    public InvalidAccountVerificationException(String message) {
        super(message);
    }
}
