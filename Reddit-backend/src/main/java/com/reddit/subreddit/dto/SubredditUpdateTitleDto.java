package com.reddit.subreddit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SubredditUpdateTitleDto(
        @NotBlank(message = "{title.required}")
        @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
        String title,
        @NotNull(message = "{moderatorId.required}")
        Long moderatorId) {
}
