package com.buildwithrani.ecommerce.category.service;

import com.buildwithrani.ecommerce.category.model.Category;
import com.buildwithrani.ecommerce.category.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // ---------------- CREATE CATEGORY ----------------
    // Used once or rarely (admin / seed)

    public Category createCategory(Category category) {

        if (categoryRepository.existsBySlug(category.getSlug())) {
            throw new RuntimeException("Category with this slug already exists");
        }

        return categoryRepository.save(category);
    }

    // ---------------- READ CATEGORIES ----------------
    // Used by frontend (filters, menus)

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryBySlug(String slug) {
        return categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
}
