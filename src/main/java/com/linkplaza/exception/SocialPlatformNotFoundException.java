package com.linkplaza.exception;

public class SocialPlatformNotFoundException extends RuntimeException {
    public SocialPlatformNotFoundException() {
    }

    public SocialPlatformNotFoundException(String message) {
        super(message);
    }
}