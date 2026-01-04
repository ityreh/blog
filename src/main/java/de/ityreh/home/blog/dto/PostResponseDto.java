package de.ityreh.home.blog.dto;

import de.ityreh.home.blog.entity.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for post responses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private PostStatus status;
    private UserResponseDto author;
    private CategoryResponseDto category;
    private Set<TagResponseDto> tags;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime publishedAt;
}
