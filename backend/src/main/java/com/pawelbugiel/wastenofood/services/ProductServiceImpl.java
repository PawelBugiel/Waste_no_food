package com.pawelbugiel.wastenofood.services;

import com.pawelbugiel.wastenofood.dtos.ProductRequest;
import com.pawelbugiel.wastenofood.dtos.ProductResponse;
import com.pawelbugiel.wastenofood.exceptions.ProductNotFoundException;
import com.pawelbugiel.wastenofood.mappers.ProductMapper;
import com.pawelbugiel.wastenofood.models.Product;
import com.pawelbugiel.wastenofood.repositories.ProductRepository;
import com.pawelbugiel.wastenofood.validators.ObjectValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ObjectValidator<ProductRequest> objectValidator;
    private static final int MAX_QUANTITY = 20_000;

    private final static Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, ObjectValidator<ProductRequest> objectValidator) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.objectValidator = objectValidator;
    }

//************** CREATE *************

    @Override
    @Transactional
    public ProductResponse createNewProductOrUpdateExisting(ProductRequest productRequest) {

        objectValidator.validate(productRequest);

        Optional<Product> existingProduct = productRepository
                .findByNameAndExpiryDate(productRequest.getName(), productRequest.getExpiryDate());

        return existingProduct.map(
                product -> handleExistingProduct(product, productRequest.getQuantity()))
                .orElseGet(() -> createNewProduct(productRequest));
    }


    private ProductResponse createNewProduct(ProductRequest productRequest) {

        Product passedProduct = productMapper.toProduct(productRequest);
        Product savedProduct = productRepository.save(passedProduct);
        return productMapper.toProductResponse(savedProduct);
    }

    private ProductResponse handleExistingProduct(Product existingProduct, Integer quantityToAdd) {

        if (existingProduct.getQuantity() + quantityToAdd > MAX_QUANTITY) {
            throw new IllegalArgumentException("Product quantity cannot exceed " + MAX_QUANTITY);
        }

        existingProduct.setQuantity(existingProduct.getQuantity() + quantityToAdd);
        Product savedProduct = productRepository.save(existingProduct);

        return productMapper.toProductResponse(savedProduct);
    }


    //************** READ *************
    @Override
    public Page<ProductResponse> findAllProducts(Pageable pageable) {

        return productRepository
                .findAll(pageable)
                .map(productMapper::toProductResponse);
    }

    @Override
    public ProductResponse findProductById(UUID id) {
        Product product = findProductByIdOrThrow(id);
        return productMapper.toProductResponse(product);
    }

    private Product findProductByIdOrThrow(UUID id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Page<ProductResponse> findProductsByPartialName(
            String partialName, Pageable pageable) {

        Page<Product> products = productRepository
                .findByPartialName(partialName, pageable);

        return products
                .map(productMapper::toProductResponse);
    }

    @Override
    public Page<ProductResponse> findProductsWithExpiredDate(Pageable pageable) {

        Page<Product> expiredProductsPage = productRepository
                .findWithExpiredDate(pageable);

        return expiredProductsPage
                .map(productMapper::toProductResponse);
    }

//************** UPDATE *************

    @Override
    public ProductResponse updateProduct(UUID id, ProductRequest productRequest) {

        objectValidator.validate(productRequest);

        Product productToUpdate = findProductByIdOrThrow(id);

        productMapper.updateProductFromRequest(productRequest, productToUpdate);
        Product savedProduct = productRepository.save(productToUpdate);

        return productMapper.toProductResponse(savedProduct);
    }

//************** DELETE *************

    @Override
    public ProductResponse deleteProductById(UUID id) {

        Product foundProduct = findProductByIdOrThrow(id);
        ProductResponse productResponse = productMapper.toProductResponse(foundProduct);

        productRepository.deleteById(id);

        return productResponse;
    }
}
