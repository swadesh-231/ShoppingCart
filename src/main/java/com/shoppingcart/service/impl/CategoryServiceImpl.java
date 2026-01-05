package com.shoppingcart.service.impl;

import com.shoppingcart.dto.CategoryRequest;
import com.shoppingcart.dto.CategoryResponse;
import com.shoppingcart.dto.CategoryView;
import com.shoppingcart.entity.Category;
import com.shoppingcart.exception.APIException;
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
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);

        List<Category> categories = categoryPage.getContent();
        if (categories.isEmpty())
            throw new APIException("No Category Found");

        List<CategoryView> content = categoryPage.getContent()
                .stream()
                .map(category -> new CategoryView(
                        category.getCategoryId(),
                        category.getCategoryName()
                ))
                .toList();
        return new CategoryResponse(
                content,
                categoryPage.getNumber(),
                categoryPage.getSize(),
                categoryPage.getTotalElements(),
                categoryPage.getTotalPages(),
                categoryPage.isLast()
        );
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        return null;
    }

    @Override
    public CategoryResponse updateCategory(Long categoryId, CategoryRequest categoryRequest) {
        return null;
    }

    @Override
    public CategoryResponse deleteCategory(Long categoryRequest) {
        return null;
    }
}
