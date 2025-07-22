package com.pawelbugiel.wastenofood.validators.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Validates if expiry date is not too far in future
 * Default max years in future is 100
 */
@Documented
@Constraint(validatedBy = ValidExpiryDateValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidExpiryDate {

    String message() default "Expired date is too far in the future";

    int maxYearsInFuture() default 100;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
