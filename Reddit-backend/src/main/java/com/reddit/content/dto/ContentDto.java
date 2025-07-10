package com.reddit.content.dto;

import com.reddit.content.ContentType;
import com.reddit.user.dto.UserDto;

import java.time.LocalDateTime;

public record ContentDto(
        Long id,
        UserDto userDto,
        ContentType contentType,
        LocalDateTime created,
        String title,
        String text,
        int score) {
}
