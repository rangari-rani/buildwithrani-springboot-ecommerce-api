package com.buildwithrani.ecommerce.category.controller;

import com.buildwithrani.ecommerce.category.model.Category;
import com.buildwithrani.ecommerce.category.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // ---------------- CREATE CATEGORY ----------------
    // Used via Postman or admin tooling (rarely)

    @PostMapping
    public ResponseEntity<Category> createCategory(
            @RequestBody Category category
    ) {
        Category createdCategory = categoryService.createCategory(category);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    // ---------------- GET ALL CATEGORIES ----------------
    // Used by frontend (filters, dropdowns, menus)

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    // ---------------- GET CATEGORY BY SLUG ----------------
    // Optional but useful

    @GetMapping("/{slug}")
    public Category getCategoryBySlug(@PathVariable String slug) {
        return categoryService.getCategoryBySlug(slug);
    }
}
