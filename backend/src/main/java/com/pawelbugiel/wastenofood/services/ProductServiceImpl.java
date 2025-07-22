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

import static com.pawelbugiel.wastenofood.dtos.ProductRequest.MAX_PRODUCT_QUANTITY;

@Service
public class ProductServiceImpl implements ProductService {

    private final static Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ObjectValidator<ProductRequest> objectValidator;

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
                product -> updateQuantityForExistingProduct(product, productRequest.getQuantity()))
                .orElseGet(() -> createNewProduct(productRequest));
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
    @Transactional
    public ProductResponse updateProduct(UUID id, ProductRequest productRequest) {

        objectValidator.validate(productRequest);

        if(productRequest.getQuantity() > MAX_PRODUCT_QUANTITY) {
            throw new IllegalArgumentException("Product quantity cannot exceed " + MAX_PRODUCT_QUANTITY);
        }

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

//************** private methods *************

    private ProductResponse createNewProduct(ProductRequest productRequest) {

        Product passedProduct = productMapper.toProduct(productRequest);
        Product savedProduct = productRepository.save(passedProduct);
        return productMapper.toProductResponse(savedProduct);
    }

    private ProductResponse updateQuantityForExistingProduct(Product existingProduct, Integer quantityToAdd) {

        checkIfUpdatedProductQuantityExceedsMaxQuantity(
                existingProduct.getQuantity() + quantityToAdd);

        existingProduct.setQuantity(existingProduct.getQuantity() + quantityToAdd);
        Product savedProduct = productRepository.save(existingProduct);

        return productMapper.toProductResponse(savedProduct);
    }

    private Product findProductByIdOrThrow(UUID id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
    private static void checkIfUpdatedProductQuantityExceedsMaxQuantity(int productQuantityAfterUpdate) {
        if (productQuantityAfterUpdate > MAX_PRODUCT_QUANTITY) {
            throw new IllegalArgumentException("Product quantity cannot exceed " + MAX_PRODUCT_QUANTITY);
        }
    }
}
