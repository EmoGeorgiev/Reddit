package com.reddit.subreddit.dto;

import jakarta.validation.constraints.NotNull;

public record ModeratorUpdateDto(
        @NotNull(message = "Moderator id cannot be null")
        Long moderatorId,
        @NotNull(message = "Updated moderator id cannot be null")
        Long updatedModerator) {
}
