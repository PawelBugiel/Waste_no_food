package com.pawelbugiel.wastenofood.services;

import com.pawelbugiel.wastenofood.dtos.ProductRequest;
import com.pawelbugiel.wastenofood.dtos.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductService {

//************** CREATE *************

    ProductResponse createNewProduct(ProductRequest productRequest);

//************** READ *************

    Page<ProductResponse> findAllProducts(Pageable pageable);

    ProductResponse findProductById(UUID id);

    Page<ProductResponse> findProductsByPartialName(String partialName,
                                                    Pageable pageable);

    Page<ProductResponse> findProductsWithExpiredDate(Pageable pageable);

//************** UPDATE *************

    ProductResponse updateProduct(UUID id, ProductRequest  productRequest);

    ProductResponse addQuantityToProduct(UUID id, Integer quantityToAdd);

//************** DELETE *************

    ProductResponse deleteProductById(UUID id);

}