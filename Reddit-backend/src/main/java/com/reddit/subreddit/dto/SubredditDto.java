package com.reddit.subreddit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record SubredditDto(
        Long id,
        @NotBlank
        @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
        String title,
        Set<Long> userIds,
        Set<Long> moderatorIds) {
}
