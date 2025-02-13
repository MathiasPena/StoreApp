package com.storeapp.storeapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.storeapp.storeapp.dto.ProductDTO;
import com.storeapp.storeapp.exception.ResourceNotFoundException;
import com.storeapp.storeapp.model.Product;
import com.storeapp.storeapp.repository.ProductRepository;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> findAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto con ID: " + id + " no encontrado"));
    }

    public void createProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        productRepository.save(product);
    }

    public void updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = findProductById(id);

        if (productDTO.getName() != null) {
            existingProduct.setName(productDTO.getName());
        }

        if (productDTO.getDescription() != null) {
            existingProduct.setDescription(productDTO.getDescription());
        }

        if (productDTO.getPrice() != null) {
            existingProduct.setPrice(productDTO.getPrice());
        }

        if (productDTO.getStock() != null) {
            existingProduct.setStock(productDTO.getStock());
        }

        productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        Product product = findProductById(id);

        if (!product.isActive()) {
            throw new IllegalStateException("El producto ya está inactivo.");
        }

        product.setActive(false);
        productRepository.save(product);
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        return dto;
    }
}
