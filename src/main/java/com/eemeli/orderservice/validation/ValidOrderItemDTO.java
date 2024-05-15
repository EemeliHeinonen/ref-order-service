package com.eemeli.orderservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = OrderItemDTOValidator.class)
public @interface ValidOrderItemDTO {
    String message() default "Invalid order item data";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}