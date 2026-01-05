package com.shoppingcart.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
        Long categoryId,
        @NotBlank(message = "Category name must not be blank")
                              String categoryName) {
}
