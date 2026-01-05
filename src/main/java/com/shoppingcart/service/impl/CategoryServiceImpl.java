package com.shoppingcart.service.impl;

import com.shoppingcart.dto.CategoryRequestDto;
import com.shoppingcart.dto.CategoryResponseDto;
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
    public CategoryResponseDto getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        if (pageNumber < 0) {throw new APIException("Page number cannot be negative");}
        if (pageSize <= 0) {throw new APIException("Page size must be greater than zero");}

        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        List<CategoryRequestDto> content = categoryPage.getContent()
                .stream()
                .map(category -> modelMapper.map(category, CategoryRequestDto.class))
                .toList();
        return CategoryResponseDto.builder()
                .content(content)
                .pageNumber(categoryPage.getNumber())
                .pageSize(categoryPage.getSize())
                .totalElements(categoryPage.getTotalElements())
                .totalPages(categoryPage.getTotalPages())
                .lastPage(categoryPage.isLast())
                .build();
    }
    @Override
    public CategoryRequestDto createCategory(CategoryRequestDto categoryRequestDto) {
        if (categoryRepository.existsByCategoryName(categoryRequestDto.getCategoryName())) {
            throw new APIException("Category with name '" + categoryRequestDto.getCategoryName() + "' already exists");
        }
        Category category = Category.builder()
                .categoryName(categoryRequestDto.getCategoryName())
                .build();
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryRequestDto.class);
    }

    @Override
    public CategoryRequestDto updateCategory(Long categoryId, CategoryRequestDto categoryRequestDto) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        if (!existingCategory.getCategoryName().equals(categoryRequestDto.getCategoryName())
                && categoryRepository.existsByCategoryName(categoryRequestDto.getCategoryName())) {
            throw new APIException("Category with name '" + categoryRequestDto.getCategoryName() + "' already exists");
        }
        existingCategory.setCategoryName(categoryRequestDto.getCategoryName());
        Category updatedCategory = categoryRepository.save(existingCategory);
        return modelMapper.map(updatedCategory, CategoryRequestDto.class);
    }

    @Override
    public CategoryRequestDto deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        categoryRepository.delete(category);
        return modelMapper.map(category, CategoryRequestDto.class);
    }
}
