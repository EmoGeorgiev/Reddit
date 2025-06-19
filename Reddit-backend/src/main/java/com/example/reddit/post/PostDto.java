package com.example.reddit.post;

import com.example.reddit.comment.CommentDto;
import com.example.reddit.content.ContentType;
import com.example.reddit.vote.VoteDto;

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
