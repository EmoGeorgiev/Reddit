package com.example.reddit.comment;

import com.example.reddit.content.ContentType;
import com.example.reddit.vote.VoteDto;

import java.time.LocalDateTime;
import java.util.Set;

public record CommentDto(
        Long id,
        Long userId,
        ContentType contentType,
        LocalDateTime created,
        String text,
        Integer score,
        Set<VoteDto> votes,
        Set<Long> savedBy,
        Boolean isDeleted,
        Set<CommentDto> replies,
        Long parentId,
        Long postId) {
}
