package com.shoppingcart.service;

import com.shoppingcart.dto.CategoryDto;
import com.shoppingcart.dto.CategoryResponseDto;

public interface CategoryService {
    CategoryResponseDto getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto);
    CategoryDto deleteCategory(Long categoryId);
}
