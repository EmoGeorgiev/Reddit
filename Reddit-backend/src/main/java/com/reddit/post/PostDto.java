package com.reddit.post;

import com.reddit.comment.CommentDto;
import com.reddit.content.ContentType;
import com.reddit.vote.VoteDto;

import java.time.LocalDateTime;
import java.util.Set;

public record PostDto(
        Long id,
        Long userId,
        ContentType contentType,
        LocalDateTime created,
        String text,
        Integer score,
        Set<VoteDto> votes,
        Set<Long> savedBy,
        Long subredditId,
        String title,
        Set<CommentDto> comments) {
}
