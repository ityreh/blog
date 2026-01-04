package de.ityreh.home.blog.controller;

import de.ityreh.home.blog.dto.TagCreateDto;
import de.ityreh.home.blog.dto.TagResponseDto;
import de.ityreh.home.blog.dto.TagUpdateDto;
import de.ityreh.home.blog.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing tags.
 */
@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
@Slf4j
public class TagController {

    private final TagService tagService;

    /**
     * Create a new tag.
     *
     * @param createDto the tag creation data
     * @return the created tag
     */
    @PostMapping
    public ResponseEntity<TagResponseDto> createTag(@Valid @RequestBody TagCreateDto createDto) {
        log.info("REST request to create tag: {}", createDto.getName());
        TagResponseDto response = tagService.createTag(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get a tag by ID.
     *
     * @param id the tag ID
     * @return the tag
     */
    @GetMapping("/{id}")
    public ResponseEntity<TagResponseDto> getTagById(@PathVariable Long id) {
        log.info("REST request to get tag by ID: {}", id);
        TagResponseDto response = tagService.getTagById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get a tag by name.
     *
     * @param name the tag name
     * @return the tag
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<TagResponseDto> getTagByName(@PathVariable String name) {
        log.info("REST request to get tag by name: {}", name);
        TagResponseDto response = tagService.getTagByName(name);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all tags.
     *
     * @return list of all tags
     */
    @GetMapping
    public ResponseEntity<List<TagResponseDto>> getAllTags() {
        log.info("REST request to get all tags");
        List<TagResponseDto> response = tagService.getAllTags();
        return ResponseEntity.ok(response);
    }

    /**
     * Update an existing tag.
     *
     * @param id        the tag ID
     * @param updateDto the update data
     * @return the updated tag
     */
    @PutMapping("/{id}")
    public ResponseEntity<TagResponseDto> updateTag(
            @PathVariable Long id,
            @Valid @RequestBody TagUpdateDto updateDto) {
        log.info("REST request to update tag with ID: {}", id);
        TagResponseDto response = tagService.updateTag(id, updateDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete a tag.
     *
     * @param id the tag ID
     * @return no content response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        log.info("REST request to delete tag with ID: {}", id);
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
