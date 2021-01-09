package com.sda.serwisaukcyjnybackend.view.shared.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class NotBeforeNowValidator implements ConstraintValidator<NotBeforeNow, LocalDateTime> {
    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        return !LocalDateTime.now().isAfter(value);
    }
}
