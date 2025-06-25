package com.reddit.savedcontent.dto;

import com.reddit.content.dto.ContentDto;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record SavedContentDto(
        Long id,
        LocalDateTime created,
        @NotNull(message = "User id cannot be null")
        Long userId,
        @NotNull(message = "ContentDto cannot be null")
        ContentDto contentDto) {
}
