package de.ityreh.home.blogservice.service;

import de.ityreh.home.blogservice.dto.*;
import de.ityreh.home.blogservice.entity.*;
import de.ityreh.home.blogservice.repository.CategoryRepository;
import de.ityreh.home.blogservice.repository.PostRepository;
import de.ityreh.home.blogservice.repository.TagRepository;
import de.ityreh.home.blogservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for managing blog posts.
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    /**
     * Create a new post.
     *
     * @param createDto the post creation data
     * @return the created post
     */
    public PostResponseDto createPost(PostCreateDto createDto) {
        log.debug("Creating post with title: {}", createDto.getTitle());

        User author = userRepository.findById(createDto.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("Author not found with ID: " + createDto.getAuthorId()));

        Post post = Post.builder()
                .title(createDto.getTitle())
                .content(createDto.getContent())
                .status(createDto.getStatus())
                .author(author)
                .build();

        if (createDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(createDto.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + createDto.getCategoryId()));
            post.setCategory(category);
        }

        if (createDto.getTagIds() != null && !createDto.getTagIds().isEmpty()) {
            Set<Tag> tags = new HashSet<>(tagRepository.findAllById(createDto.getTagIds()));
            if (tags.size() != createDto.getTagIds().size()) {
                throw new IllegalArgumentException("One or more tag IDs not found");
            }
            post.setTags(tags);
        }

        if (post.getStatus() == PostStatus.PUBLISHED) {
            post.setPublishedAt(LocalDateTime.now());
        }

        post = postRepository.save(post);
        log.info("Post created successfully with ID: {}", post.getId());

        return toResponseDto(post);
    }

    /**
     * Get a post by ID.
     *
     * @param id the post ID
     * @return the post
     */
    @Transactional(readOnly = true)
    public PostResponseDto getPostById(Long id) {
        log.debug("Fetching post with ID: {}", id);
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + id));
        return toResponseDto(post);
    }

    /**
     * Get all posts.
     *
     * @return list of all posts
     */
    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPosts() {
        log.debug("Fetching all posts");
        return postRepository.findAll().stream()
                .map(this::toResponseDto)
                .toList();
    }

    /**
     * Get posts by author ID.
     *
     * @param authorId the author ID
     * @return list of posts by the author
     */
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostsByAuthor(Long authorId) {
        log.debug("Fetching posts by author ID: {}", authorId);
        return postRepository.findByAuthorId(authorId).stream()
                .map(this::toResponseDto)
                .toList();
    }

    /**
     * Get posts by status.
     *
     * @param status the post status
     * @return list of posts with the given status
     */
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostsByStatus(PostStatus status) {
        log.debug("Fetching posts with status: {}", status);
        return postRepository.findByStatus(status).stream()
                .map(this::toResponseDto)
                .toList();
    }

    /**
     * Update an existing post.
     *
     * @param id        the post ID
     * @param updateDto the update data
     * @return the updated post
     */
    public PostResponseDto updatePost(Long id, PostUpdateDto updateDto) {
        log.debug("Updating post with ID: {}", id);

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + id));

        if (updateDto.getTitle() != null) {
            post.setTitle(updateDto.getTitle());
        }

        if (updateDto.getContent() != null) {
            post.setContent(updateDto.getContent());
        }

        if (updateDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(updateDto.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + updateDto.getCategoryId()));
            post.setCategory(category);
        }

        if (updateDto.getTagIds() != null) {
            Set<Tag> tags = new HashSet<>(tagRepository.findAllById(updateDto.getTagIds()));
            if (tags.size() != updateDto.getTagIds().size()) {
                throw new IllegalArgumentException("One or more tag IDs not found");
            }
            post.setTags(tags);
        }

        if (updateDto.getStatus() != null && !post.getStatus().equals(updateDto.getStatus())) {
            PostStatus oldStatus = post.getStatus();
            post.setStatus(updateDto.getStatus());
            
            if (updateDto.getStatus() == PostStatus.PUBLISHED && oldStatus == PostStatus.DRAFT) {
                post.setPublishedAt(LocalDateTime.now());
            }
        }

        post = postRepository.save(post);
        log.info("Post updated successfully with ID: {}", post.getId());

        return toResponseDto(post);
    }

    /**
     * Delete a post.
     *
     * @param id the post ID
     */
    public void deletePost(Long id) {
        log.debug("Deleting post with ID: {}", id);

        if (!postRepository.existsById(id)) {
            throw new IllegalArgumentException("Post not found with ID: " + id);
        }

        postRepository.deleteById(id);
        log.info("Post deleted successfully with ID: {}", id);
    }

    /**
     * Publish a post.
     *
     * @param id the post ID
     * @return the published post
     */
    public PostResponseDto publishPost(Long id) {
        log.debug("Publishing post with ID: {}", id);

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + id));

        post.publish();
        post = postRepository.save(post);
        log.info("Post published successfully with ID: {}", id);

        return toResponseDto(post);
    }

    /**
     * Convert Post entity to PostResponseDto.
     */
    private PostResponseDto toResponseDto(Post post) {
        UserResponseDto authorDto = UserResponseDto.builder()
                .id(post.getAuthor().getId())
                .username(post.getAuthor().getUsername())
                .email(post.getAuthor().getEmail())
                .firstName(post.getAuthor().getFirstName())
                .lastName(post.getAuthor().getLastName())
                .build();

        CategoryResponseDto categoryDto = null;
        if (post.getCategory() != null) {
            categoryDto = CategoryResponseDto.builder()
                    .id(post.getCategory().getId())
                    .name(post.getCategory().getName())
                    .description(post.getCategory().getDescription())
                    .build();
        }

        Set<TagResponseDto> tagDtos = post.getTags().stream()
                .map(tag -> TagResponseDto.builder()
                        .id(tag.getId())
                        .name(tag.getName())
                        .description(tag.getDescription())
                        .build())
                .collect(Collectors.toSet());

        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .status(post.getStatus())
                .author(authorDto)
                .category(categoryDto)
                .tags(tagDtos)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .publishedAt(post.getPublishedAt())
                .build();
    }
}
