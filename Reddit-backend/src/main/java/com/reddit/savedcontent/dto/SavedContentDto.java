package com.reddit.savedcontent.dto;

import com.reddit.content.dto.ContentDto;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record SavedContentDto(
        Long id,
        LocalDateTime created,
        @NotNull(message = "{userId.required}")
        Long userId,
        @NotNull(message = "{contentDto.required}")
        ContentDto contentDto) {
}
