package com.sda.serwisaukcyjnybackend.view.shared.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Constraint(validatedBy = NotBeforeNowValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBeforeNow {
    String message() default "Minimum date is now";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
