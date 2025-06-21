package com.reddit.subreddit;

public record SubredditUpdateTitleDto(
        String title,
        Long moderatorId) {
}
