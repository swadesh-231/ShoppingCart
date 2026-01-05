package com.shoppingcart.service;

import com.shoppingcart.dto.CategoryRequest;
import com.shoppingcart.dto.CategoryResponse;

public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    CategoryResponse updateCategory(Long categoryId,CategoryRequest categoryRequest);
    CategoryResponse deleteCategory(Long CategoryId);
}
