package com.eemeli.orderservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class NotOlderThanDaysValidator implements ConstraintValidator<NotOlderThanDays, LocalDate> {
    private int days;

    public void initialize(NotOlderThanDays constraint) {
        this.days = constraint.days();
    }

    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        if (date == null) {
            return true;
        }

        LocalDate today = LocalDate.now();
        return !date.isBefore(today.minusDays(days));
    }
}
