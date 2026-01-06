package com.shoppingcart.service.impl;

import com.shoppingcart.dto.ProductDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProductDto addProduct(Long categoryId, ProductDto productDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        Boolean exists = productRepository
                .existsByCategoryAndProductName(category, productDto.getProductName().trim());
        if (exists) {
            throw new APIException("Product already exists in this category");
        }
        Product product = modelMapper.map(productDto, Product.class);
        product.setProductName(productDto.getProductName().trim());
        product.setImage("default.png");
        product.setCategory(category);

        BigDecimal specialPrice = product.getPrice()
                .subtract(product.getPrice().multiply(product.getDiscount()).divide(BigDecimal.valueOf(100)))
                .setScale(2, RoundingMode.HALF_UP);
        product.setSpecialPrice(specialPrice);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductResponseDto getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Product> pageProducts = productRepository.findAll(pageDetails);

        List<Product> products = pageProducts.getContent();
        List<ProductDto> productDtos = products.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();

        return ProductResponseDto.builder()
                .contents(productDtos)
                .pageNumber(pageProducts.getNumber())
                .pageSize(pageProducts.getSize())
                .totalElements(pageProducts.getTotalElements())
                .totalPages(pageProducts.getTotalPages())
                .last(pageProducts.isLast())
                .build();
    }

    @Override
    public ProductResponseDto searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy,
            String sortOrder) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Product> pageProducts = productRepository.findByCategoryOrderByPriceAsc(category, pageDetails);

        List<Product> products = pageProducts.getContent();

        if (products.isEmpty()) {
            throw new APIException(category.getCategoryName() + " category does not have any products");
        }
        List<ProductDto> productDtos = products.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();

        return ProductResponseDto.builder()
                .contents(productDtos)
                .pageNumber(pageProducts.getNumber())
                .pageSize(pageProducts.getSize())
                .totalElements(pageProducts.getTotalElements())
                .totalPages(pageProducts.getTotalPages())
                .last(pageProducts.isLast())
                .build();
    }

    @Override
    public ProductResponseDto searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize,
            String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> productPage = productRepository.findByProductNameContainingIgnoreCase(keyword, pageable);

        if (productPage.isEmpty()) {
            throw new APIException("No products found with keyword: " + keyword);
        }

        List<ProductDto> productDtos = productPage.getContent().stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();

        return ProductResponseDto.builder()
                .contents(productDtos)
                .pageNumber(productPage.getNumber())
                .pageSize(productPage.getSize())
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .last(productPage.isLast())
                .build();
    }

    @Override
    public ProductDto updateProduct(Long productId, ProductDto productDto) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        existingProduct.setProductName(productDto.getProductName().trim());
        existingProduct.setDescription(productDto.getProductDescription());
        existingProduct.setQuantity(productDto.getQuantity());
        existingProduct.setDiscount(productDto.getDiscount());
        existingProduct.setPrice(productDto.getPrice());
        BigDecimal specialPrice = productDto.getPrice()
                .subtract(productDto.getPrice().multiply(productDto.getDiscount()).divide(BigDecimal.valueOf(100)))
                .setScale(2, RoundingMode.HALF_UP);
        existingProduct.setSpecialPrice(specialPrice);
        Product savedProduct = productRepository.save(existingProduct);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductDto deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        productRepository.delete(product);
        return modelMapper.map(product, ProductDto.class);
    }
}
