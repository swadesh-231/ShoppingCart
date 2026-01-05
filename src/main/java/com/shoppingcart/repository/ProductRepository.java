package com.shoppingcart.repository;

import com.shoppingcart.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
    Boolean existsByCategory_CategoryIdAndProductName(Long category_categoryId, String productName);
}
