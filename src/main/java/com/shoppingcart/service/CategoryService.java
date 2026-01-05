package com.shoppingcart.service;

import com.shoppingcart.dto.CategoryRequestDto;
import com.shoppingcart.dto.CategoryResponseDto;

public interface CategoryService {
    CategoryResponseDto getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    CategoryRequestDto createCategory(CategoryRequestDto categoryRequestDto);
    CategoryRequestDto updateCategory(Long categoryId, CategoryRequestDto categoryRequestDto);
    CategoryRequestDto deleteCategory(Long categoryId);
}
