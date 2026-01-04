package de.ityreh.home.blogservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for tag responses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagResponseDto {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
