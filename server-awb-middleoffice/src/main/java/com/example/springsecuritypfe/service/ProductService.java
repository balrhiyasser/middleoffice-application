package com.example.springsecuritypfe.service;

import java.util.List;

import com.example.springsecuritypfe.model.Product;

public interface ProductService {
	
    Product saveProduct(Product product);

    Product updateProduct(Product product);

    void deleteProduct(Long productId);

    Long numberOfProducts();

    List<Product> findAllProducts();
}
