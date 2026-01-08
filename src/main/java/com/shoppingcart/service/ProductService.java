package com.shoppingcart.service;

import com.shoppingcart.dto.ProductDto;
import com.shoppingcart.dto.ProductResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductDto addProduct(Long categoryId, ProductDto productDto);
    ProductResponseDto getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    ProductResponseDto searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    ProductResponseDto searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    ProductDto updateProduct(Long productId, ProductDto productDto);
    ProductDto deleteProduct(Long productId);
    ProductDto updateProductImage(Long productId, MultipartFile image) throws IOException;
}
