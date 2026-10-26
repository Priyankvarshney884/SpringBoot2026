package com.example.Spring_starter.controller;

import com.example.Spring_starter.dto.ProductResponseDto;
import com.example.Spring_starter.mapper.ProductMapper;
import com.example.Spring_starter.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Read-only catalog endpoint used by the storefront product discovery page. */
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping
    public List<ProductResponseDto> getProducts() {
        return productService.getAllProducts().stream()
                .map(productMapper::toResponse)
                .toList();
    }
}
