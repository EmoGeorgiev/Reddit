package com.reddit.subreddit;

public record ModeratorUpdateDto(
        Long moderatorId,
        Long updatedModerator) {
}
