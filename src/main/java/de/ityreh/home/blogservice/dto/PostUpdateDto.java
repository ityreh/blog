package de.ityreh.home.blogservice.dto;

import de.ityreh.home.blogservice.entity.PostStatus;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * DTO for updating an existing post.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateDto {

    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;

    private String content;

    private Long categoryId;

    private Set<Long> tagIds;

    private PostStatus status;
}
