package com.linkplaza.common;

public class AppConstants {
    public static final Long JWT_AUTH_EXPIRATION = 3 * 86400000L; // 3 dias
    public static final Long JWT_RESET_PASSWORD_EXPIRATION = 15 * 60 * 1000L; // 15 minutos
    public static final Long VERIFICATION_CODE_EXPIRATION = 15 * 60 * 1000L; // 15 minutos

    public static final String AUTHORITIES = "authorities";
    public static final String TOKEN_HEADER = "Jwt-Token";
    public static final String OPTIONS_HTTP_METHOD = "options";
    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String ACCESS_DENIED = "You don't have permission to access this resource.";
    public static final String FORBIDDEN = "You need to be logged in to access this resource.";
    public static final String NOT_FOUND_ERROR = "404 Not Found.";
    public static final String METHOD_NOT_ALLOWED = "This operation is not allowed. Only %s operations are allowed.";
    public static final String INVALID_TOKEN = "Token is not valid.";
    public static final String INTERNAL_SERVER_ERROR = "An error occurred while processing your request.";
    public static final String INCORRECT_CREDENTIALS = "Incorrect username or password.";
    public static final String TOKEN_UNVERIFIABLE = "Token cannot be verified.";

    public static final String EMAIL_PATTERN = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    public static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>.]).{8,}$";

}
