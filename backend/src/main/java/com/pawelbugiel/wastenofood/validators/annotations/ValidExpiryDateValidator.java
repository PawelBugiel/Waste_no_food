package com.pawelbugiel.wastenofood.validators.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ValidExpiryDateValidator implements ConstraintValidator<ValidExpiryDate, LocalDate> {

    private int maxYearsInFuture;

    @Override
    public void initialize(ValidExpiryDate constraintAnnotation) {
        this.maxYearsInFuture = constraintAnnotation.maxYearsInFuture();
    }

    @Override
    public boolean isValid(LocalDate expiryDate, ConstraintValidatorContext context) {

        // @NotNull already exists in ProductRequest
        if (expiryDate == null) {
            return true;
        }

        LocalDate maxAllowedDate = LocalDate.now().plusYears(this.maxYearsInFuture);

        return !expiryDate.isAfter(maxAllowedDate);
    }
}
