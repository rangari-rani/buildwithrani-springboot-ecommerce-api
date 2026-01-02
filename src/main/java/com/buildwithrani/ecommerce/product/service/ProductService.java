package com.buildwithrani.ecommerce.product.service;

import com.buildwithrani.ecommerce.product.dto.ProductRequestDTO;
import com.buildwithrani.ecommerce.product.dto.ProductResponseDTO;
import com.buildwithrani.ecommerce.product.model.Product;
import com.buildwithrani.ecommerce.product.model.ProductStatus;
import com.buildwithrani.ecommerce.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // ---------------- ADMIN METHODS ----------------

    public ProductResponseDTO createProduct(ProductRequestDTO request) {
        Product product = new Product();

        product.setName(request.getName());
        product.setSlug(request.getSlug());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setImage(request.getImage());
        product.setCategoryId(request.getCategoryId());

        product.setIsFeatured(
                request.getIsFeatured() != null && request.getIsFeatured()
        );

        product.setStatus(ProductStatus.ACTIVE);

        Product savedProduct = productRepository.save(product);
        return mapToResponse(savedProduct);
    }

    // ---------------- USER METHODS ----------------

    public List<ProductResponseDTO> getAllActiveProducts() {
        return productRepository.findByStatus(ProductStatus.ACTIVE)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ProductResponseDTO getProductBySlug(String slug) {
        Product product = productRepository.findBySlug(slug)
                .filter(p -> p.getStatus() == ProductStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return mapToResponse(product);
    }

    public List<ProductResponseDTO> getFeaturedProducts() {
        return productRepository
                .findByIsFeaturedTrueAndStatus(ProductStatus.ACTIVE)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDTO> getProductsByCategory(Long categoryId) {
        return productRepository
                .findByCategoryIdAndStatus(categoryId, ProductStatus.ACTIVE)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDTO> getNewProducts() {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(14);

        return productRepository
                .findByStatusAndCreatedAtAfter(ProductStatus.ACTIVE, cutoff)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void updateProductStatus(Long productId, ProductStatus status) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setStatus(status);

        productRepository.save(product);
    }

    public void updateFeaturedStatus(Long productId, boolean isFeatured) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setIsFeatured(isFeatured);

        productRepository.save(product);
    }

    // ---------------- MAPPER ----------------

    private ProductResponseDTO mapToResponse(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setSlug(product.getSlug());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setImage(product.getImage());
        dto.setCategoryId(product.getCategoryId());

        return dto;
    }
}
