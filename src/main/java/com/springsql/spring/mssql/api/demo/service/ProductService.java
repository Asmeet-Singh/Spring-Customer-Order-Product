package com.springsql.spring.mssql.api.demo.service;

import com.springsql.spring.mssql.api.demo.dao.ProductDao;
import com.springsql.spring.mssql.api.demo.dto.ProductRequest;
import com.springsql.spring.mssql.api.demo.exceptions.ProductDoesNotExistException;
import com.springsql.spring.mssql.api.demo.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ProductService {

    @Autowired
    private ProductDao productDao;

    public Product createProduct(ProductRequest productRequest) {
//        Product product = new Product();
//        product.setPrice(productRequest.getPrice());
//        product.setName(productRequest.getName());
//        product.setQuantity(productRequest.getQuantity());
        Product buildProduct = Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();

        log.info("Creating product: {}", buildProduct);
        Product savedProduct = productDao.save(buildProduct);
        log.info("Created product: {}", savedProduct);
        return savedProduct;
    }

    public Product updateProduct(Integer id, ProductRequest productRequest) {
        Optional<Product> optionalProduct = productDao.findById(id);
        optionalProduct.orElseThrow(() -> new IllegalArgumentException("Product with id: " + id + " does not exist." +
                " Can't update the product."));

        Product buildProduct = Product.builder()
                .id(id)
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();
        Product savedProduct = productDao.save(buildProduct);
        log.info("Updated product with id: {}", id);
        return savedProduct;
    }

    public Integer calculatePrice(Integer productId, Integer quantity) {
        Product product = findByProductId(productId);
        return product.getPrice() * quantity;
    }

    public Product findByProductId(Integer productId) {
        return productDao.findById(productId).orElseThrow(ProductDoesNotExistException::new);
    }

    public void updateQuantity(int i) {
        throw new RuntimeException();
    }
}
