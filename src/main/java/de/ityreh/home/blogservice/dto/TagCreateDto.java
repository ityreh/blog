package de.ityreh.home.blogservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating a new tag.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagCreateDto {

    @NotBlank(message = "Tag name is required")
    @Size(max = 50, message = "Tag name must not exceed 50 characters")
    private String name;

    @Size(max = 200, message = "Description must not exceed 200 characters")
    private String description;
}
