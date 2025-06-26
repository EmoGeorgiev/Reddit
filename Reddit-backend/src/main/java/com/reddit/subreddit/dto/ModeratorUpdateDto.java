package com.reddit.subreddit.dto;

import jakarta.validation.constraints.NotNull;

public record ModeratorUpdateDto(
        @NotNull(message = "{moderatorId.required}")
        Long moderatorId,
        @NotNull(message = "{moderatorId.required}")
        Long updatedModerator) {
}
