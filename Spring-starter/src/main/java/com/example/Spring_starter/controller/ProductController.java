package com.example.Spring_starter.controller;

import com.example.Spring_starter.dto.ProductRequestDto;
import com.example.Spring_starter.dto.ProductResponseDto;
import com.example.Spring_starter.mapper.ProductMapper;
import com.example.Spring_starter.model.Product;
import com.example.Spring_starter.service.ProductService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/search")
    public List<ProductResponseDto> searchProducts(@RequestParam String name) {
        return productService.searchProductsByName(name).stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(productMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto request) {
        Product product = productMapper.toEntity(request);
        Product savedProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productMapper.toResponse(savedProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequestDto request
    ) {
        Product updatedProduct = productMapper.toEntity(request);
        Optional<Product> savedProduct = productService.updateProduct(id, updatedProduct);

        // Unknown ID par 404 dene se client ko pata chalta hai ki koi product update nahi hua.
        return savedProduct
                .map(productMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        // Service result se successful deletion aur unknown ID ko alag HTTP status dete hain.
        if (!productService.deleteProduct(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
