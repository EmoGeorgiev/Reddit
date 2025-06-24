package com.reddit.subreddit.dto;

public record SubredditUpdateTitleDto(
        String title,
        Long moderatorId) {
}
