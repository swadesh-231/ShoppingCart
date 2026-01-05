package com.shoppingcart.service.impl;

import com.shoppingcart.dto.CategoryDto;
import com.shoppingcart.dto.CategoryResponse;
import com.shoppingcart.entity.Category;
import com.shoppingcart.exception.APIException;
import com.shoppingcart.exception.ResourceNotFoundException;
import com.shoppingcart.repository.CategoryRepository;
import com.shoppingcart.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        if (pageNumber < 0) {throw new APIException("Page number cannot be negative");}
        if (pageSize <= 0) {throw new APIException("Page size must be greater than zero");}

        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        List<CategoryDto> content = categoryPage.getContent()
                .stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .toList();
        return CategoryResponse.builder()
                .content(content)
                .pageNumber(categoryPage.getNumber())
                .pageSize(categoryPage.getSize())
                .totalElements(categoryPage.getTotalElements())
                .totalPages(categoryPage.getTotalPages())
                .lastPage(categoryPage.isLast())
                .build();
    }
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        if (categoryRepository.existsByCategoryName(categoryDto.getCategoryName())) {
            throw new APIException("Category with name '" + categoryDto.getCategoryName() + "' already exists");
        }
        Category category = Category.builder()
                .categoryName(categoryDto.getCategoryName())
                .build();
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        if (!existingCategory.getCategoryName().equals(categoryDto.getCategoryName())
                && categoryRepository.existsByCategoryName(categoryDto.getCategoryName())) {
            throw new APIException("Category with name '" + categoryDto.getCategoryName() + "' already exists");
        }
        existingCategory.setCategoryName(categoryDto.getCategoryName());
        Category updatedCategory = categoryRepository.save(existingCategory);
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        categoryRepository.delete(category);
        return modelMapper.map(category, CategoryDto.class);
    }
}
