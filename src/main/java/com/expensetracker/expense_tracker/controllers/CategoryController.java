package com.expensetracker.expense_tracker.controllers;

import com.expensetracker.expense_tracker.models.Category;
import com.expensetracker.expense_tracker.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Get all categories
    @GetMapping
    public List<Category> getAllCategories() {
        logger.info("Fetching all categories");
        return categoryService.getAllCategories();
    }

    // Create a new category
    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        logger.info("Creating a new category with name: {}", category.getName());
        return categoryService.createCategory(category);
    }

    // Get category by ID
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        logger.info("Fetching category with ID: {}", id);
        Category category = categoryService.getCategoryById(id);
        if (category != null) {
            logger.info("Category found with ID: {}", id);
            return ResponseEntity.ok(category);
        } else {
            logger.warn("Category not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    // Update category by ID
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        logger.info("Updating category with ID: {}", id);
        Category updatedCategory = categoryService.updateCategory(id, category);
        if (updatedCategory != null) {
            logger.info("Category updated with ID: {}", id);
            return ResponseEntity.ok(updatedCategory);
        } else {
            logger.warn("Category not found for update with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    // Delete category by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        logger.info("Deleting category with ID: {}", id);
        Category category = categoryService.getCategoryById(id);
        if (category != null) {
            categoryService.deleteCategory(id);
            logger.info("Category deleted with ID: {}", id);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("Category not found for deletion with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}
