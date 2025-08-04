package com.pawelbugiel.wastenofood.dtos;

import com.pawelbugiel.wastenofood.configs.ProductValidationConfig;
import com.pawelbugiel.wastenofood.validators.annotations.ValidExpiryDate;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record ProductRequest(
        @NotBlank(message = "Product name cannot be empty or contain only whitespaces")
        @Pattern(regexp = ProductValidationConfig.PRODUCT_NAME_VALIDATION_REGEX,
                message = "Product name must start with at least 3 alphanumeric characters")
        @Size(min = 3, max = ProductValidationConfig.MAX_NAME_LENGTH,
                message = "Product name length must be between 3 and " + ProductValidationConfig.MAX_NAME_LENGTH + " characters long")
        String name,

        @NotNull(message = "Quantity is required and must be a number")
        @Min(value = 1, message = "Quantity must be at least 1")
        @Max(value = ProductValidationConfig.MAX_PRODUCT_QUANTITY,
                message = "Quantity must not exceed " + ProductValidationConfig.MAX_PRODUCT_QUANTITY)
        Integer quantity,

        @NotNull(message = "Expiry date cannot be null")
        @FutureOrPresent(message = "Expiry date must be today or in the future")
        @ValidExpiryDate(maxYearsInFuture = ProductValidationConfig.MAX_EXPIRY_YEARS_IN_FUTURE,
                message = "Expiry date cannot be more than " + ProductValidationConfig.MAX_EXPIRY_YEARS_IN_FUTURE + " years in the future")
        LocalDate expiryDate
) {}