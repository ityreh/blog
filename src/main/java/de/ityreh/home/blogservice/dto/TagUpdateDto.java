package de.ityreh.home.blogservice.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for updating an existing tag.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagUpdateDto {

    @Size(max = 50, message = "Tag name must not exceed 50 characters")
    private String name;

    @Size(max = 200, message = "Description must not exceed 200 characters")
    private String description;
}
