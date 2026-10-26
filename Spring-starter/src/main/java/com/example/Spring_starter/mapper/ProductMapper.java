package com.example.Spring_starter.mapper;

import com.example.Spring_starter.dto.ProductRequestDto;
import com.example.Spring_starter.dto.ProductResponseDto;
import com.example.Spring_starter.model.Product;
import org.springframework.stereotype.Component;

/** Product API DTOs aur JPA entity ke beech field mapping karta hai. */
@Component
public class ProductMapper {

    public Product toEntity(ProductRequestDto request) {
        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());
        product.setCategory(request.category());
        product.setImageUrl(request.imageUrl());
        product.setActive(request.active());
        // Request se ID/timestamps copy nahi hote; yeh server side par generate hote hain.
        return product;
    }

    public ProductResponseDto toResponse(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory(),
                product.getImageUrl(),
                product.getActive(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
