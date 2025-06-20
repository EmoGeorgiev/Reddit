package com.reddit.subreddit;

import com.reddit.post.PostDto;

import java.util.Set;

public record SubredditDto(
        Long id,
        String title,
        Set<PostDto> posts,
        Set<Long> userIds,
        Set<Long> moderatorIds) {
}
