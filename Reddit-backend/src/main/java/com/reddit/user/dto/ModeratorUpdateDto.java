package com.reddit.user.dto;

import jakarta.validation.constraints.NotNull;

public record ModeratorUpdateDto(
        @NotNull(message = "{moderatorId.required}")
        Long moderatorId,
        @NotNull(message = "{moderatorUsername.required}")
        String updatedModeratorUsername) {
}
