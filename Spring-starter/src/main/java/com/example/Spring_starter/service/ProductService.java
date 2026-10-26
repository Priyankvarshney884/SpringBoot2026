package com.example.Spring_starter.service;

import com.example.Spring_starter.model.Product;
import com.example.Spring_starter.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        // Naye product ka ID database generate kare, request se aaya ID nahi.
        product.setId(null);
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public boolean deleteProduct(Long id) {
        // Delete se pehle existence check karke unknown ID ko controller tak clearly batate hain.
        if (!productRepository.existsById(id)) {
            return false;
        }

        productRepository.deleteById(id);
        return true;
    }

    public Optional<Product> updateProduct(Long id, Product updatedProduct) {
        // Pehle existing row dhoondhne se missing ID par accidental insert nahi hota.
        return productRepository.findById(id).map(existingProduct -> {
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setStock(updatedProduct.getStock());
            existingProduct.setCategory(updatedProduct.getCategory());
            existingProduct.setImageUrl(updatedProduct.getImageUrl());
            existingProduct.setActive(updatedProduct.getActive());
            return productRepository.save(existingProduct);
        });
    }
}
