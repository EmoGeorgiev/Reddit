package com.reddit.subreddit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SubredditUpdateTitleDto(
        @NotBlank(message = "{title.required}")
        @Size(min = 1, max = 100, message = "{title.size}")
        String title,
        @NotNull(message = "{moderatorId.required}")
        Long moderatorId) {
}
