package de.ityreh.home.blog.controller;

import de.ityreh.home.blog.dto.CategoryCreateDto;
import de.ityreh.home.blog.dto.CategoryResponseDto;
import de.ityreh.home.blog.dto.CategoryUpdateDto;
import de.ityreh.home.blog.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing categories.
 */
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Create a new category.
     *
     * @param createDto the category creation data
     * @return the created category
     */
    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@Valid @RequestBody CategoryCreateDto createDto) {
        log.info("REST request to create category: {}", createDto.getName());
        CategoryResponseDto response = categoryService.createCategory(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get a category by ID.
     *
     * @param id the category ID
     * @return the category
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Long id) {
        log.info("REST request to get category by ID: {}", id);
        CategoryResponseDto response = categoryService.getCategoryById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get a category by name.
     *
     * @param name the category name
     * @return the category
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryResponseDto> getCategoryByName(@PathVariable String name) {
        log.info("REST request to get category by name: {}", name);
        CategoryResponseDto response = categoryService.getCategoryByName(name);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all categories.
     *
     * @return list of all categories
     */
    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        log.info("REST request to get all categories");
        List<CategoryResponseDto> response = categoryService.getAllCategories();
        return ResponseEntity.ok(response);
    }

    /**
     * Update an existing category.
     *
     * @param id        the category ID
     * @param updateDto the update data
     * @return the updated category
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryUpdateDto updateDto) {
        log.info("REST request to update category with ID: {}", id);
        CategoryResponseDto response = categoryService.updateCategory(id, updateDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete a category.
     *
     * @param id the category ID
     * @return no content response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        log.info("REST request to delete category with ID: {}", id);
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
