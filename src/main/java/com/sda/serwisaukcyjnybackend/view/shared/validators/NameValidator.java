package com.sda.serwisaukcyjnybackend.view.shared.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class NameValidator implements ConstraintValidator<Name, String> {
    private static final Pattern PATTERN = Pattern.compile("^[A-ZĄĘŚŁŻŹĆŃÓ][a-ząęśłżźćńó]+$");
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return PATTERN.matcher(value).matches();
    }
}
