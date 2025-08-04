package com.pawelbugiel.wastenofood.configs;

public final class ProductValidationConfig {
    private ProductValidationConfig() {}

    public static final int MAX_PRODUCT_QUANTITY = 20_000;
    public static final String PRODUCT_NAME_VALIDATION_REGEX = "^[a-zA-Z0-9]{3}.*$";
    public static final int MAX_EXPIRY_YEARS_IN_FUTURE = 110;
    public static final int MAX_NAME_LENGTH = 33;
    public static final String PRODUCT_NAME_SEARCH_VALIDATION_REGEX = "^[a-zA-Z0-9].*";
}