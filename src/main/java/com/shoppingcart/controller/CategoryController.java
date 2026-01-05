package com.shoppingcart.controller;

import com.shoppingcart.config.AppConstants;
import com.shoppingcart.dto.CategoryRequest;
import com.shoppingcart.dto.CategoryResponse;
import com.shoppingcart.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name = "PageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "PageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(categoryResponse);
    }
    @PostMapping("/admin/categories")
    public ResponseEntity<CategoryResponse> addCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(categoryService.createCategory(categoryRequest));
    }
    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryResponse> deleteCategory(@PathVariable Long categoryId){
        CategoryResponse deletedCategory = categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(deletedCategory);
    }
    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(@Valid @RequestBody CategoryRequest categoryRequest, @PathVariable Long categoryId){
        CategoryResponse savedCategoryDTO = categoryService.updateCategory(categoryId, categoryRequest);
        return ResponseEntity.ok(savedCategoryDTO);
    }
}
