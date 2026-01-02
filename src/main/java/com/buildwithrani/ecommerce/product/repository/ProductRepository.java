package com.buildwithrani.ecommerce.product.repository;

import com.buildwithrani.ecommerce.product.model.Product;
import com.buildwithrani.ecommerce.product.model.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Find single product by slug (for product detail page)
    Optional<Product> findBySlug(String slug);

    // List all ACTIVE products (user-facing)
    List<Product> findByStatus(ProductStatus status);

    // Homepage: featured products
    List<Product> findByIsFeaturedTrueAndStatus(ProductStatus status);

    // Category-based listing (user-facing)
    List<Product> findByCategoryIdAndStatus(Long categoryId, ProductStatus status);

    List<Product> findByStatusAndCreatedAtAfter(
            ProductStatus status,
            LocalDateTime date
    );

}
