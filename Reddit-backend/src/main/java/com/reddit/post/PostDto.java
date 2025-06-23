package com.reddit.post;

import com.reddit.content.ContentType;
import com.reddit.vote.VoteDto;

import java.time.LocalDateTime;
import java.util.Set;

public record PostDto(
        Long id,
        Long userId,
        LocalDateTime created,
        String text,
        int score,
        Set<VoteDto> votes,
        Set<Long> savedBy,
        Long subredditId,
        String title) {
}
