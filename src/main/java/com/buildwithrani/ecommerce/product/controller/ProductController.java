package com.buildwithrani.ecommerce.product.controller;

import com.buildwithrani.ecommerce.product.dto.ProductResponseDTO;
import com.buildwithrani.ecommerce.product.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ---------------- ALL PRODUCTS ----------------
    // Used for product listing page

    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAllActiveProducts();
    }

    // ---------------- PRODUCT DETAIL ----------------
    // Used for product detail page (/products/{slug})

    @GetMapping("/{slug}")
    public ProductResponseDTO getProductBySlug(@PathVariable String slug) {
        return productService.getProductBySlug(slug);
    }

    // ---------------- FEATURED PRODUCTS ----------------
    // Used for homepage sections (Featured / New)

    @GetMapping("/featured")
    public List<ProductResponseDTO> getFeaturedProducts() {
        return productService.getFeaturedProducts();
    }

    @GetMapping("/new")
    public List<ProductResponseDTO> getNewProducts() {
        return productService.getNewProducts();
    }


    // ---------------- CATEGORY PRODUCTS ----------------
    // Used for category-based listing

    @GetMapping("/category/{categoryId}")
    public List<ProductResponseDTO> getProductsByCategory(
            @PathVariable Long categoryId
    ) {
        return productService.getProductsByCategory(categoryId);
    }
}
