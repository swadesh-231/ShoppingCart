package com.shoppingcart.service;

import com.shoppingcart.dto.CategoryDto;
import com.shoppingcart.dto.CategoryResponse;

public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto);
    CategoryDto deleteCategory(Long categoryId);
}
