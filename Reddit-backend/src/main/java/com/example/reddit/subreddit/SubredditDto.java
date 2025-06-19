package com.example.reddit.subreddit;

import com.example.reddit.post.PostDto;

import java.util.Set;

public record SubredditDto(
        Long id,
        String title,
        Set<PostDto> posts,
        Set<Long> userIds,
        Set<Long> moderatorIds) {
}
