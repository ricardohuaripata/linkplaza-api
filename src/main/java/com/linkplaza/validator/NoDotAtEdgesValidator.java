package com.linkplaza.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.linkplaza.annotation.NoDotAtEdges;

public class NoDotAtEdgesValidator implements ConstraintValidator<NoDotAtEdges, String> {

    @Override
    public void initialize(NoDotAtEdges constraintAnnotation) {
    }

    @Override
    public boolean isValid(String url, ConstraintValidatorContext context) {
        if (url == null) {
            return true;
        }
        return !url.startsWith(".") && !url.endsWith(".");
    }
}