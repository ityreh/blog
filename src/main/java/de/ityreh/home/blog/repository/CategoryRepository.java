package de.ityreh.home.blog.repository;

import de.ityreh.home.blog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Category entity operations.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Find a category by name.
     *
     * @param name the category name
     * @return an Optional containing the category if found
     */
    Optional<Category> findByName(String name);

    /**
     * Check if a category exists with the given name.
     *
     * @param name the category name to check
     * @return true if a category exists with the given name
     */
    boolean existsByName(String name);
}
