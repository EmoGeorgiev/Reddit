package com.example.reddit.subreddit;

import com.example.reddit.post.PostDto;

import java.util.Set;

public record SubredditDto(
        Long id,
        Set<PostDto> posts,
        Set<Integer> userIds,
        Set<Integer> moderatorIds) {
}
