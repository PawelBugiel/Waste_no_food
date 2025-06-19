package com.pawelbugiel.wastenofood.services;

import com.pawelbugiel.wastenofood.dtos.ProductRequest;
import com.pawelbugiel.wastenofood.dtos.ProductResponse;
import com.pawelbugiel.wastenofood.exceptions.ProductNotFoundException;
import com.pawelbugiel.wastenofood.mappers.ProductMapper;
import com.pawelbugiel.wastenofood.models.Product;
import com.pawelbugiel.wastenofood.repositories.ProductRepository;
import com.pawelbugiel.wastenofood.validators.ObjectValidator;
import com.pawelbugiel.wastenofood.validators.PageableValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final PageableValidator pageableValidator;
    private final ObjectValidator<ProductRequest> objectValidator;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, PageableValidator pageValidator, ObjectValidator<ProductRequest> objectValidator) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.pageableValidator = pageValidator;
        this.objectValidator = objectValidator;
    }

//************** CREATE *************

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest productRequest) {

        objectValidator.validate(productRequest);
        log.info("Attempting to create or update product with name: [{}]", productRequest.getName()); // tu jest problem 'null

        Optional<Product> existingProduct = productRepository
                .findByNameAndExpiryDate(productRequest.getName(), productRequest.getExpiryDate());

        Product savedProduct;

        if (existingProduct.isEmpty()) {
            log.info("Product with name [{}] does not exist. Creating a new one.", productRequest.getName());

            Product passedProduct = productMapper.toProduct(productRequest);
            savedProduct = productRepository.save(passedProduct);
        } else {
            Product actualProduct = existingProduct.get();

            log.info("Product with name [{}] already exists (ID: {}). Updating quantity.", actualProduct.getName(), actualProduct.getId());

            actualProduct.setQuantity(actualProduct.getQuantity() + productRequest.getQuantity());
            savedProduct = productRepository.save(actualProduct);
        }
        return productMapper.toProductResponse(savedProduct);
    }

    //************** READ *************
    @Override
    public Page<ProductResponse> findAllProducts(int page, Integer pageSize, String sortBy, Sort.Direction sortDirection) {
        log.info("Fetching all products - page: {}, size: {}, sort: {},{}", page, pageSize, sortBy, sortDirection);

        Pageable pageable = createPageable(page, pageSize, sortBy, sortDirection);

        Page<ProductResponse> productPage = productRepository
                .findAll(pageable)
                .map(productMapper::toProductResponse);

        log.debug("Found {} products in total.", productPage.getTotalElements());

        return productPage;
    }

    @Override
    public ProductResponse findProductById(UUID id) {
        log.info("Fetching product with ID: {}", id);

        Product product = findProductByIdOrThrow(id);

        return productMapper.toProductResponse(product);
    }

    private Product findProductByIdOrThrow(UUID id) {
        log.debug("Searching for product with ID: {}", id);

        return productRepository
                .findById(id)
                .orElseThrow(() -> {
                    log.error("Product with ID: {} not found. Throwing ProductNotFoundException.", id);
                    return new ProductNotFoundException(id);
                });
    }

    @Override
    public Page<ProductResponse> findProductsByPartialName(
            String partialName, int page, Integer pageSize, String sortBy, Sort.Direction sortDirection) {
        log.info("Finding products by partial name: [{}] - page: {}, size: {}", partialName, page, pageSize);

        Pageable pageable = createPageable(page, pageSize, sortBy, sortDirection);

        Page<ProductResponse> products = productRepository
                .findByPartialName(partialName, pageable)
                .map(productMapper::toProductResponse);

        log.debug("Found {} products matching the partial name.", products.getTotalElements());
        return products;

    }

    private Pageable createPageable(int page, Integer pageSize, String sortBy, Sort.Direction sortDirection) {
        return pageableValidator
                .validatePageable(page, pageSize, sortBy, sortDirection);
    }

    @Override
    public Page<ProductResponse> findProductsWithExpiredDate(
            int page, Integer pageSize, String sortBy, Sort.Direction sortDirection) {
        log.info("Finding products with expired date - page: {}, size: {}", page, pageSize);

        Pageable pageable = createPageable(page, pageSize, sortBy, sortDirection);

        Page<ProductResponse> expiredProductsPage = productRepository
                .findWithExpiredDate(pageable)
                .map(productMapper::toProductResponse);

        log.debug("Found {} expired products.", expiredProductsPage.getTotalElements());
        return expiredProductsPage;

    }

//************** UPDATE *************

    @Override
    @Transactional
    public ProductResponse updateProduct(UUID id, ProductRequest productRequest) {
        log.info("Attempting to update product with ID: {}", id);
        objectValidator.validate(productRequest);

        Product productToUpdate = findProductByIdOrThrow(id);
        log.debug("Product to update found. Applying changes for product: {}", productToUpdate.getName());

        productMapper.updateProductFromRequest(productRequest, productToUpdate);

        Product savedProduct = productRepository.save(productToUpdate);

        log.info("Successfully updated product with ID: {}", savedProduct.getId());
        return productMapper.toProductResponse(savedProduct);
    }

//************** DELETE *************

    @Override
    @Transactional
    public ProductResponse deleteProductById(UUID id) {
        log.info("Attempting to delete product with ID: {}", id);
        Product foundProduct = findProductByIdOrThrow(id);
        ProductResponse productResponse = productMapper.toProductResponse(foundProduct);

        productRepository.deleteById(id);

        log.info("Successfully deleted product with ID: {} and name: [{}]", id, foundProduct.getName());
        return productResponse;
    }
}
