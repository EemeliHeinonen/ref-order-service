package com.eemeli.orderservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ProductDTOValidator.class)
public @interface ValidProductDTO {
    String message() default "Invalid product data";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
