package com.shoppingcart.service.impl;

import com.shoppingcart.dto.ProductRequestDto;
import com.shoppingcart.dto.ProductResponseDto;
import com.shoppingcart.entity.Category;
import com.shoppingcart.entity.Product;
import com.shoppingcart.exception.APIException;
import com.shoppingcart.exception.ResourceNotFoundException;
import com.shoppingcart.repository.CategoryRepository;
import com.shoppingcart.repository.ProductRepository;
import com.shoppingcart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    @Override
    public ProductResponseDto addProduct(Long categoryId, ProductRequestDto productRequestDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category", "categoryId", categoryId));
        Boolean exists = productRepository
                .existsByCategory_CategoryIdAndProductName((categoryId), productRequestDto.getProductName());
        if (exists) {
            throw new APIException("Product already exists in this category");
        }
        Product product = modelMapper.map(productRequestDto, Product.class);
        product.setImage("default.png");
        product.setCategory(category);

        double specialPrice = product.getPrice()
                - (product.getDiscount() / 100.0) * product.getPrice();

        product.setSpecialPrice(specialPrice);

        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductResponseDto.class);

    }

    @Override
    public ProductResponseDto getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        return null;
    }

    @Override
    public ProductResponseDto searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        return null;
    }

    @Override
    public ProductResponseDto searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        return null;
    }

    @Override
    public ProductResponseDto updateProduct(Long productId, ProductRequestDto product) {
        return null;
    }

    @Override
    public ProductResponseDto deleteProduct(Long productId) {
        return null;
    }
}
