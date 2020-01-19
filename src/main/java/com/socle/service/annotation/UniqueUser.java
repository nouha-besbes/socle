package com.socle.service.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.socle.service.validator.UniqueUserValidator;

@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUserValidator.class)
public @interface UniqueUser {
    String message() default "The given user mail is already in use";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
