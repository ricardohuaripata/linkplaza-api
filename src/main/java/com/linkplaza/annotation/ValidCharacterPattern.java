package com.linkplaza.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.linkplaza.validator.CharacterPatternValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = CharacterPatternValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCharacterPattern {
    String message() default "URL may only contain letters, numbers, underscores, and periods.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}