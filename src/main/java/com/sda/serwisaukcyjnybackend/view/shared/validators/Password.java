package com.sda.serwisaukcyjnybackend.view.shared.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Constraint(validatedBy = PasswordValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "Password must contains at least 6 characters, at least one uppercase and lowercase letter, number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
