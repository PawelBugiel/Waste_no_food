package com.pawelbugiel.wastenofood.controllers;

import com.pawelbugiel.wastenofood.dtos.AddQuantityRequest;
import com.pawelbugiel.wastenofood.dtos.ProductRequest;
import com.pawelbugiel.wastenofood.dtos.ProductResponse;
import com.pawelbugiel.wastenofood.services.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

//@SuppressWarnings("unused")
@RestController
@RequestMapping("api/products")
@Validated
public class ProductController {

    private static final String PARTIAL_NAME_REGEX = "^[a-zA-Z0-9].*";

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createNewProduct(
            @RequestBody @Valid ProductRequest productRequest,
            UriComponentsBuilder uriBuilder) {

        ProductResponse resultProductResponse = productService
                .createNewProduct(productRequest);

        String resourceUri = getResourceUri(uriBuilder, resultProductResponse);

        return ResponseEntity
                .status(201)
                .header("Location", resourceUri)
                .body(resultProductResponse);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> findAllProducts(Pageable pageable) {

        Page<ProductResponse> productResponses = productService
                .findAllProducts(pageable);

        return ResponseEntity
                .ok(productResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findProductById(@PathVariable UUID id) {

        ProductResponse productResponse = productService
                .findProductById(id);

        return ResponseEntity
                .ok(productResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponse>> findProductsByPartialName(
            @RequestParam @Pattern(regexp = PARTIAL_NAME_REGEX) String partialName,
            Pageable pageable) {

        Page<ProductResponse> productResponses = productService
                .findProductsByPartialName(partialName, pageable);

        return ResponseEntity
                .ok(productResponses);
    }

    @GetMapping("/expired")
    public ResponseEntity<Page<ProductResponse>> findProductsWithExpiredDate(Pageable pageable) {

        Page<ProductResponse> foundProducts = productService
                .findProductsWithExpiredDate(pageable);

        return ResponseEntity
                .ok(foundProducts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable UUID id,
            @RequestBody @Valid ProductRequest productRequest) {

        ProductResponse updatedProductResponse = productService
                .updateProduct(id, productRequest);

        return ResponseEntity
                .ok(updatedProductResponse);
    }

    @PatchMapping("/{id}/quantity")
    public ResponseEntity<ProductResponse> addQuantityToProduct(
            @PathVariable UUID id,
            @RequestBody @Valid AddQuantityRequest request) {

        ProductResponse updatedProduct = productService
                .addQuantityToProduct(id, request.quantityToAdd());

        return ResponseEntity
                .ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponse> deleteProduct(
            @PathVariable UUID id) {

        ProductResponse deletedProductResponse = productService
                .deleteProductById(id);

        return ResponseEntity
                .ok(deletedProductResponse);
    }

    private static String getResourceUri(UriComponentsBuilder uriBuilder,
                                         ProductResponse resultProductResponse) {
        return uriBuilder
                .path("/api/product/products/{id}")
                .buildAndExpand(resultProductResponse.id())
                .toUriString();
    }
}