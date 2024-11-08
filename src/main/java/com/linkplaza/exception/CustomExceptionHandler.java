package com.linkplaza.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.linkplaza.common.AppConstants;
import com.linkplaza.response.ErrorResponse;

@RestControllerAdvice
public class CustomExceptionHandler {
    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status,
            String message,
            Map<String, List<ValidationError>> validationErrors) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status)
                .statusCode(status.value())
                .message(message)
                .reason(status.getReasonPhrase())
                .validationErrors(validationErrors)
                .timestamp(new Date())
                .build();
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationError(MethodArgumentNotValidException e) {
        Map<String, List<ValidationError>> validationErrors = new HashMap<>();
        ResponseEntity<ErrorResponse> errorResponseResponseEntity = buildErrorResponse(HttpStatus.BAD_REQUEST,
                "Validation Error", validationErrors);

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        for (FieldError fieldError : fieldErrors) {
            List<ValidationError> validationErrorList = Objects.requireNonNull(errorResponseResponseEntity.getBody())
                    .getValidationErrors().get(fieldError.getField());
            if (validationErrorList == null) {
                validationErrorList = new ArrayList<>();
                errorResponseResponseEntity.getBody().getValidationErrors().put(fieldError.getField(),
                        validationErrorList);
            }
            ValidationError validationError = ValidationError.builder()
                    .code(fieldError.getCode())
                    .message(fieldError.getDefaultMessage())
                    .build();
            validationErrorList.add(validationError);
        }

        return errorResponseResponseEntity;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, AppConstants.ACCESS_DENIED, null);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException e) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, AppConstants.INCORRECT_CREDENTIALS, null);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleTokenExpiredException(TokenExpiredException e) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, AppConstants.INVALID_TOKEN, null);
    }

    @ExceptionHandler(SignatureVerificationException.class)
    public ResponseEntity<ErrorResponse> handleSignatureVerificationException(SignatureVerificationException e) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, AppConstants.INVALID_TOKEN, null);
    }

    @ExceptionHandler(AuthenticationServiceException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationServiceException(AuthenticationServiceException e) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, AppConstants.INCORRECT_CREDENTIALS, null);
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<ErrorResponse> handleNoResultException(NoResultException e) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, AppConstants.NOT_FOUND_ERROR, null);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedMethodError(HttpRequestMethodNotSupportedException e) {
        HttpMethod supportedMethod = Objects.requireNonNull(e.getSupportedHttpMethods()).iterator().next();
        return buildErrorResponse(HttpStatus.METHOD_NOT_ALLOWED,
                String.format(AppConstants.METHOD_NOT_ALLOWED, supportedMethod),
                null);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage(), null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException e) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null);
    }

}