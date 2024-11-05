package com.linkplaza.common;

public class AppConstants {
    public static final Long AUTH_TOKEN_EXPIRATION = 3 * 86400000L; // 3 dias
    public static final Long RESET_PASSWORD_TOKEN_EXPIRATION = 15 * 60 * 1000L; // 15 minutos

    public static final String AUTHORITIES = "authorities";
    public static final String TOKEN_HEADER = "Jwt-Token";
    public static final String OPTIONS_HTTP_METHOD = "options";
    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String USER_NOT_FOUND = "No user found.";
    public static final String EMAIL_ALREADY_TAKEN = "The email is already taken.";

}
