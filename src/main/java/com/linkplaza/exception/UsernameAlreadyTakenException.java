package com.linkplaza.exception;

public class UsernameAlreadyTakenException extends RuntimeException {
    public UsernameAlreadyTakenException() {
    }

    public UsernameAlreadyTakenException(String message) {
        super(message);
    }
}