package com.shoppingcart.service;

import com.shoppingcart.dto.ProductRequestDto;
import com.shoppingcart.dto.ProductResponseDto;

public interface ProductService {
    ProductResponseDto addProduct(Long categoryId, ProductRequestDto productRequestDto);
    ProductResponseDto getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    ProductResponseDto searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    ProductResponseDto searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    ProductResponseDto updateProduct(Long productId, ProductRequestDto productRequestDto);
    ProductResponseDto deleteProduct(Long productId);
}
