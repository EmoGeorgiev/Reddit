package com.reddit.savedcontent.dto;

import com.reddit.content.dto.ContentDto;

import java.time.LocalDateTime;

public record SavedContentDto(
        Long id,
        LocalDateTime created,
        Long userId,
        ContentDto contentDto) {
}
