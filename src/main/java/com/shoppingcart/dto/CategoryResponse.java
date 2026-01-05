package com.shoppingcart.dto;

import java.util.List;

public record CategoryResponse(
        List<CategoryView> content,
        Integer pageNumber,
        Integer pageSize,
        Long totalElements,
        Integer totalPages,
        boolean lastPage
) {}
