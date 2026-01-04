package de.ityreh.home.blogservice.dto;

import de.ityreh.home.blogservice.entity.PostStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * DTO for creating a new post.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateDto {

    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    @NotNull(message = "Author ID is required")
    private Long authorId;

    private Long categoryId;

    private Set<Long> tagIds;

    @Builder.Default
    private PostStatus status = PostStatus.DRAFT;
}
