package com.pawelbugiel.wastenofood.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Data Transfer Object for creating or updating a product request.
 */

@Getter
@ToString
@Builder
@AllArgsConstructor
public class ProductRequest {

    private static final String PRODUCT_NAME_REGEX = "^[a-zA-Z0-9]{3}.*$";
    private static final int PRODUCT_MAX_QUANTITY = 20_000;

    @NotBlank(message = "Product name cannot be empty or contain only whitespaces")
    @Pattern(regexp = "^[a-zA-Z0-9]{3}.*$", message = "Product name must start with at least 3 alphanumeric characters")
    @Size(min = 3, max = 33, message = "The name must be between 3 and 33 characters long")
    private final String name;

    @Min(value = 1, message = "Quantity must be at least 1")
    @Max(value = PRODUCT_MAX_QUANTITY, message = "Quantity must not exceed " + PRODUCT_MAX_QUANTITY)
    private final Integer quantity;

    @NotNull(message = "Expiry date cannot be null")
    @FutureOrPresent(message = "Expiry date must be today or in the future")
    private final LocalDate expiryDate;
}
