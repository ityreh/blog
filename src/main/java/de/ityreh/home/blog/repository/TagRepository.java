package de.ityreh.home.blog.repository;

import de.ityreh.home.blog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Tag entity operations.
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    /**
     * Find a tag by name.
     *
     * @param name the tag name
     * @return an Optional containing the tag if found
     */
    Optional<Tag> findByName(String name);

    /**
     * Check if a tag exists with the given name.
     *
     * @param name the tag name to check
     * @return true if a tag exists with the given name
     */
    boolean existsByName(String name);
}
