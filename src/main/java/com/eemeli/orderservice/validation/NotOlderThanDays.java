package com.eemeli.orderservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = NotOlderThanDaysValidator.class)
public @interface NotOlderThanDays {
    String message() default "The date must not be older than specified days";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int days();
}
