package com.pawelbugiel.wastenofood.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductProperties {
    NAME("name"),
    QUANTITY("quantity"),
    EXPIRY_DATE("expiryDate");

    private final String value;
}
