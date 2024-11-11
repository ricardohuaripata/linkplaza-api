package com.linkplaza.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.linkplaza.validator.NoDotAtEdgesValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = NoDotAtEdgesValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NoDotAtEdges {
    String message() default "URL cannot start or end with a period.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}