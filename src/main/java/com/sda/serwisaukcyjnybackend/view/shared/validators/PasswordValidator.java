package com.sda.serwisaukcyjnybackend.view.shared.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, char[]> {
    private static final Pattern PATTERN = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{6,}$");

    @Override
    public boolean isValid(char[] value, ConstraintValidatorContext context) {
        return PATTERN.matcher(new String(value)).matches();
    }
}
