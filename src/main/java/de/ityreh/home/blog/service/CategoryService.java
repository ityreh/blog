package de.ityreh.home.blog.service;

import de.ityreh.home.blog.dto.CategoryCreateDto;
import de.ityreh.home.blog.dto.CategoryResponseDto;
import de.ityreh.home.blog.dto.CategoryUpdateDto;
import de.ityreh.home.blog.entity.Category;
import de.ityreh.home.blog.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for managing categories.
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Create a new category.
     *
     * @param createDto the category creation data
     * @return the created category
     */
    public CategoryResponseDto createCategory(CategoryCreateDto createDto) {
        log.debug("Creating category with name: {}", createDto.getName());

        if (categoryRepository.existsByName(createDto.getName())) {
            throw new IllegalArgumentException("Category already exists with name: " + createDto.getName());
        }

        Category category = Category.builder()
                .name(createDto.getName())
                .description(createDto.getDescription())
                .build();

        category = categoryRepository.save(category);
        log.info("Category created successfully with ID: {}", category.getId());

        return toResponseDto(category);
    }

    /**
     * Get a category by ID.
     *
     * @param id the category ID
     * @return the category
     */
    @Transactional(readOnly = true)
    public CategoryResponseDto getCategoryById(Long id) {
        log.debug("Fetching category with ID: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + id));
        return toResponseDto(category);
    }

    /**
     * Get a category by name.
     *
     * @param name the category name
     * @return the category
     */
    @Transactional(readOnly = true)
    public CategoryResponseDto getCategoryByName(String name) {
        log.debug("Fetching category with name: {}", name);
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with name: " + name));
        return toResponseDto(category);
    }

    /**
     * Get all categories.
     *
     * @return list of all categories
     */
    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getAllCategories() {
        log.debug("Fetching all categories");
        return categoryRepository.findAll().stream()
                .map(this::toResponseDto)
                .toList();
    }

    /**
     * Update an existing category.
     *
     * @param id        the category ID
     * @param updateDto the update data
     * @return the updated category
     */
    public CategoryResponseDto updateCategory(Long id, CategoryUpdateDto updateDto) {
        log.debug("Updating category with ID: {}", id);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + id));

        if (updateDto.getName() != null && !updateDto.getName().equals(category.getName())) {
            if (categoryRepository.existsByName(updateDto.getName())) {
                throw new IllegalArgumentException("Category already exists with name: " + updateDto.getName());
            }
            category.setName(updateDto.getName());
        }

        if (updateDto.getDescription() != null) {
            category.setDescription(updateDto.getDescription());
        }

        category = categoryRepository.save(category);
        log.info("Category updated successfully with ID: {}", category.getId());

        return toResponseDto(category);
    }

    /**
     * Delete a category.
     *
     * @param id the category ID
     */
    public void deleteCategory(Long id) {
        log.debug("Deleting category with ID: {}", id);

        if (!categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Category not found with ID: " + id);
        }

        categoryRepository.deleteById(id);
        log.info("Category deleted successfully with ID: {}", id);
    }

    /**
     * Convert Category entity to CategoryResponseDto.
     */
    private CategoryResponseDto toResponseDto(Category category) {
        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }
}
