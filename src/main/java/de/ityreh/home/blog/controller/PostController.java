package de.ityreh.home.blog.controller;

import de.ityreh.home.blog.dto.PostCreateDto;
import de.ityreh.home.blog.dto.PostResponseDto;
import de.ityreh.home.blog.dto.PostUpdateDto;
import de.ityreh.home.blog.entity.PostStatus;
import de.ityreh.home.blog.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing blog posts.
 */
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    /**
     * Create a new post.
     *
     * @param createDto the post creation data
     * @return the created post
     */
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody PostCreateDto createDto) {
        log.info("REST request to create post: {}", createDto.getTitle());
        PostResponseDto response = postService.createPost(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get a post by ID.
     *
     * @param id the post ID
     * @return the post
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id) {
        log.info("REST request to get post by ID: {}", id);
        PostResponseDto response = postService.getPostById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all posts.
     *
     * @return list of all posts
     */
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        log.info("REST request to get all posts");
        List<PostResponseDto> response = postService.getAllPosts();
        return ResponseEntity.ok(response);
    }

    /**
     * Get posts by author ID.
     *
     * @param authorId the author ID
     * @return list of posts by the author
     */
    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<PostResponseDto>> getPostsByAuthor(@PathVariable Long authorId) {
        log.info("REST request to get posts by author ID: {}", authorId);
        List<PostResponseDto> response = postService.getPostsByAuthor(authorId);
        return ResponseEntity.ok(response);
    }

    /**
     * Get posts by status.
     *
     * @param status the post status
     * @return list of posts with the given status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PostResponseDto>> getPostsByStatus(@PathVariable PostStatus status) {
        log.info("REST request to get posts by status: {}", status);
        List<PostResponseDto> response = postService.getPostsByStatus(status);
        return ResponseEntity.ok(response);
    }

    /**
     * Update an existing post.
     *
     * @param id        the post ID
     * @param updateDto the update data
     * @return the updated post
     */
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostUpdateDto updateDto) {
        log.info("REST request to update post with ID: {}", id);
        PostResponseDto response = postService.updatePost(id, updateDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Publish a post.
     *
     * @param id the post ID
     * @return the published post
     */
    @PostMapping("/{id}/publish")
    public ResponseEntity<PostResponseDto> publishPost(@PathVariable Long id) {
        log.info("REST request to publish post with ID: {}", id);
        PostResponseDto response = postService.publishPost(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete a post.
     *
     * @param id the post ID
     * @return no content response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        log.info("REST request to delete post with ID: {}", id);
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
