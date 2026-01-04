package de.ityreh.home.blogservice.repository;

import de.ityreh.home.blogservice.entity.Post;
import de.ityreh.home.blogservice.entity.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Post entity operations.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * Find all posts by author ID.
     *
     * @param authorId the author's ID
     * @return a list of posts by the author
     */
    List<Post> findByAuthorId(Long authorId);

    /**
     * Find all posts by status.
     *
     * @param status the post status
     * @return a list of posts with the given status
     */
    List<Post> findByStatus(PostStatus status);

    /**
     * Find all posts by category ID.
     *
     * @param categoryId the category ID
     * @return a list of posts in the category
     */
    List<Post> findByCategoryId(Long categoryId);

    /**
     * Find all posts containing a specific tag.
     *
     * @param tagId the tag ID
     * @return a list of posts with the tag
     */
    List<Post> findByTagsId(Long tagId);
}
