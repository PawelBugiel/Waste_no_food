package com.pawelbugiel.wastenofood.services;

import com.pawelbugiel.wastenofood.dtos.ProductRequest;
import com.pawelbugiel.wastenofood.dtos.ProductResponse;
import com.pawelbugiel.wastenofood.exceptions.ProductNotFoundException;
import com.pawelbugiel.wastenofood.exceptions.ProductQuantityException;
import com.pawelbugiel.wastenofood.mappers.ProductMapper;
import com.pawelbugiel.wastenofood.models.Product;
import com.pawelbugiel.wastenofood.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.pawelbugiel.wastenofood.dtos.ProductRequest.MAX_PRODUCT_QUANTITY;

@Service
public class ProductServiceImpl implements ProductService {

    private final static Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

//************** CREATE *************

    @Override
    @Transactional
    public ProductResponse createNewProduct(ProductRequest productRequest) {

        Product passedProduct = productMapper.toProduct(productRequest);
        Product savedProduct = productRepository.save(passedProduct);

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

        checkIfProductQuantityExceedsMaxQuantity(productRequest.getQuantity());

        Product productToUpdate = findProductByIdOrThrow(id);
        productMapper.updateProductFromRequest(productRequest, productToUpdate);
        Product savedProduct = productRepository.save(productToUpdate);

        return productMapper.toProductResponse(savedProduct);
    }

    @Override
    @Transactional
    public ProductResponse addQuantityToProduct(UUID id, Integer quantity) {

        Product foundProduct = findProductByIdOrThrow(id);

        int newQuantity = foundProduct.getQuantity() + quantity;
        checkIfProductQuantityExceedsMaxQuantity(newQuantity);

        foundProduct.setQuantity(newQuantity);
        Product savedProduct = productRepository.save(foundProduct);

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

    private Product findProductByIdOrThrow(UUID id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    private static void checkIfProductQuantityExceedsMaxQuantity(int productQuantity) {
        if (productQuantity > MAX_PRODUCT_QUANTITY) {
            throw new ProductQuantityException("" + productQuantity);
        }
    }
}
