package com.pawelbugiel.wastenofood.exceptions;

import static com.pawelbugiel.wastenofood.dtos.ProductRequest.MAX_PRODUCT_QUANTITY;

public class ProductQuantityException extends RuntimeException {
    public ProductQuantityException(String message) {
        super("Product quantity exceeded max quantity :" + MAX_PRODUCT_QUANTITY + " Passed quantity: "  + message);
    }
}