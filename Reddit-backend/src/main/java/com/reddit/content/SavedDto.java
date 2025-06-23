package com.reddit.content;

import com.reddit.user.UserDto;

import java.time.LocalDateTime;

public record SavedDto(
        Long id,
        UserDto userDto,
        LocalDateTime created,
        String title,
        String text,
        int score) {
}
