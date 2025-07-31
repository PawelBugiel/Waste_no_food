package com.pawelbugiel.wastenofood.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddQuantityRequest(
        @NotNull
        @Min(value = 1, message = "Quantity must be at least 1")
        Integer quantityToAdd
) {}