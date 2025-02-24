package com.tjalia.userprofile.validator;

import com.tjalia.userprofile.validator.impl.DateValidatorImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateValidatorImpl.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateValidator {
    String message() default "Invalid date format. Expected yyyy-MM-dd.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
