package com.shoppingcart.controller;

import com.shoppingcart.dto.ProductRequestDto;
import com.shoppingcart.dto.ProductResponseDto;
import com.shoppingcart.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductResponseDto> addProduct(@PathVariable Long categoryId,@Valid @RequestBody ProductRequestDto productRequestDto){
        ProductResponseDto savedProductDTO = productService.addProduct(categoryId, productRequestDto);
        return ResponseEntity.ok(savedProductDTO);
    }

}
