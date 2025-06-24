package com.reddit.subreddit.dto;

import java.util.Set;

public record SubredditDto(
        Long id,
        String title,
        Set<Long> userIds,
        Set<Long> moderatorIds) {
}
