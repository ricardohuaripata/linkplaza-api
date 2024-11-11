package com.linkplaza.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.linkplaza.annotation.ValidCharacterPattern;

public class CharacterPatternValidator implements ConstraintValidator<ValidCharacterPattern, String> {

    private static final String PATTERN = "^[a-zA-Z0-9_.]+$";

    @Override
    public void initialize(ValidCharacterPattern constraintAnnotation) {
    }

    @Override
    public boolean isValid(String url, ConstraintValidatorContext context) {
        if (url == null) {
            return true;
        }
        return url.matches(PATTERN);
    }
}