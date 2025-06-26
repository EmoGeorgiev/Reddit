package com.reddit.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record PostDto(
        Long id,
        Long userId,
        LocalDateTime created,
        @NotBlank(message = "Text must not be blank")
        @Size(min = 1, max = 5000, message = "Text must be between 1 and 5000 characters")
        String text,
        Integer score,
        @NotNull(message = "Subreddit id cannot be null")
        Long subredditId,
        @NotBlank(message = "Title must not be blank")
        @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
        String title) {
}
