package com.pawelbugiel.wastenofood.dtos;

import com.pawelbugiel.wastenofood.validators.annotations.ValidExpiryDate;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * Data Transfer Object for creating or updating a product request.
 */

@Getter
@ToString
@Builder
@AllArgsConstructor
public class ProductRequest {

    public static final int MAX_PRODUCT_QUANTITY = 20_000;

    private static final String PRODUCT_NAME_REGEX = "^[a-zA-Z0-9]{3}.*$";
    private static final int MAX_EXPIRY_YEARS_IN_FUTURE = 110;
    private static final int MAX_NAME_LENGTH = 33;

    @NotBlank(message = "Product name cannot be empty or contain only whitespaces")
    @Pattern(regexp = "^[a-zA-Z0-9]{3}.*$",
            message = "Product name must start with at least 3 alphanumeric characters")
    @Size(min = 3, max = MAX_NAME_LENGTH,
            message = "Product name length must be between 3 and " + MAX_NAME_LENGTH + " characters long")
    private final String name;

    @Min(value = 1, message = "Quantity must be at least 1")
    @Max(value = MAX_PRODUCT_QUANTITY,
            message = "Quantity must not exceed " + MAX_PRODUCT_QUANTITY)

    private final Integer quantity;

    @NotNull(message = "Expiry date cannot be null")
    @FutureOrPresent(message = "Expiry date must be today or in the future")
    @ValidExpiryDate(maxYearsInFuture = MAX_EXPIRY_YEARS_IN_FUTURE,
            message = "Expiry date cannot be more than " + MAX_EXPIRY_YEARS_IN_FUTURE + " years in the future")
    private final LocalDate expiryDate;

}
