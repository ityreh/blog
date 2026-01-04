package de.ityreh.home.blogservice.service;

import de.ityreh.home.blogservice.dto.TagCreateDto;
import de.ityreh.home.blogservice.dto.TagResponseDto;
import de.ityreh.home.blogservice.dto.TagUpdateDto;
import de.ityreh.home.blogservice.entity.Tag;
import de.ityreh.home.blogservice.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for managing tags.
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TagService {

    private final TagRepository tagRepository;

    /**
     * Create a new tag.
     *
     * @param createDto the tag creation data
     * @return the created tag
     */
    public TagResponseDto createTag(TagCreateDto createDto) {
        log.debug("Creating tag with name: {}", createDto.getName());

        if (tagRepository.existsByName(createDto.getName())) {
            throw new IllegalArgumentException("Tag already exists with name: " + createDto.getName());
        }

        Tag tag = Tag.builder()
                .name(createDto.getName())
                .description(createDto.getDescription())
                .build();

        tag = tagRepository.save(tag);
        log.info("Tag created successfully with ID: {}", tag.getId());

        return toResponseDto(tag);
    }

    /**
     * Get a tag by ID.
     *
     * @param id the tag ID
     * @return the tag
     */
    @Transactional(readOnly = true)
    public TagResponseDto getTagById(Long id) {
        log.debug("Fetching tag with ID: {}", id);
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tag not found with ID: " + id));
        return toResponseDto(tag);
    }

    /**
     * Get a tag by name.
     *
     * @param name the tag name
     * @return the tag
     */
    @Transactional(readOnly = true)
    public TagResponseDto getTagByName(String name) {
        log.debug("Fetching tag with name: {}", name);
        Tag tag = tagRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Tag not found with name: " + name));
        return toResponseDto(tag);
    }

    /**
     * Get all tags.
     *
     * @return list of all tags
     */
    @Transactional(readOnly = true)
    public List<TagResponseDto> getAllTags() {
        log.debug("Fetching all tags");
        return tagRepository.findAll().stream()
                .map(this::toResponseDto)
                .toList();
    }

    /**
     * Update an existing tag.
     *
     * @param id        the tag ID
     * @param updateDto the update data
     * @return the updated tag
     */
    public TagResponseDto updateTag(Long id, TagUpdateDto updateDto) {
        log.debug("Updating tag with ID: {}", id);

        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tag not found with ID: " + id));

        if (updateDto.getName() != null && !updateDto.getName().equals(tag.getName())) {
            if (tagRepository.existsByName(updateDto.getName())) {
                throw new IllegalArgumentException("Tag already exists with name: " + updateDto.getName());
            }
            tag.setName(updateDto.getName());
        }

        if (updateDto.getDescription() != null) {
            tag.setDescription(updateDto.getDescription());
        }

        tag = tagRepository.save(tag);
        log.info("Tag updated successfully with ID: {}", tag.getId());

        return toResponseDto(tag);
    }

    /**
     * Delete a tag.
     *
     * @param id the tag ID
     */
    public void deleteTag(Long id) {
        log.debug("Deleting tag with ID: {}", id);

        if (!tagRepository.existsById(id)) {
            throw new IllegalArgumentException("Tag not found with ID: " + id);
        }

        tagRepository.deleteById(id);
        log.info("Tag deleted successfully with ID: {}", id);
    }

    /**
     * Convert Tag entity to TagResponseDto.
     */
    private TagResponseDto toResponseDto(Tag tag) {
        return TagResponseDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .description(tag.getDescription())
                .createdAt(tag.getCreatedAt())
                .updatedAt(tag.getUpdatedAt())
                .build();
    }
}
