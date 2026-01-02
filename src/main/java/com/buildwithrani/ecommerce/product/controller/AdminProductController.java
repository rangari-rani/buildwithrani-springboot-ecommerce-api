package com.buildwithrani.ecommerce.product.controller;

import com.buildwithrani.ecommerce.product.dto.ProductRequestDTO;
import com.buildwithrani.ecommerce.product.dto.ProductResponseDTO;
import com.buildwithrani.ecommerce.product.model.ProductStatus;
import com.buildwithrani.ecommerce.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/products")
@PreAuthorize("hasRole('ADMIN')")
public class AdminProductController {

    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    // ---------------- CREATE PRODUCT ----------------
    // Admin adds product (Postman / Admin UI later)

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(
            @RequestBody ProductRequestDTO request
    ) {
        ProductResponseDTO createdProduct =
                productService.createProduct(request);

        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    // ---------------- ACTIVATE / DEACTIVATE PRODUCT ----------------
    // Soft visibility control (no delete)

    @PatchMapping("/{productId}/status")
    public ResponseEntity<Void> updateProductStatus(
            @PathVariable Long productId,
            @RequestParam ProductStatus status
    ) {
        productService.updateProductStatus(productId, status);
        return ResponseEntity.noContent().build();
    }

    // ---------------- FEATURE / UNFEATURE PRODUCT ----------------
    // Homepage control

    @PatchMapping("/{productId}/feature")
    public ResponseEntity<Void> updateFeaturedStatus(
            @PathVariable Long productId,
            @RequestParam boolean isFeatured
    ) {
        productService.updateFeaturedStatus(productId, isFeatured);
        return ResponseEntity.noContent().build();
    }
}
