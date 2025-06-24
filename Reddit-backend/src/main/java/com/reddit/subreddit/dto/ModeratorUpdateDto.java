package com.reddit.subreddit.dto;

public record ModeratorUpdateDto(
        Long moderatorId,
        Long updatedModerator) {
}
