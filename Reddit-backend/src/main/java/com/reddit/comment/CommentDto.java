package com.reddit.comment;

import com.reddit.content.ContentType;
import com.reddit.vote.VoteDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public record CommentDto(
        Long id,
        Long userId,
        LocalDateTime created,
        String text,
        int score,
        Set<VoteDto> votes,
        Set<Long> savedBy,
        boolean isDeleted,
        List<CommentDto> replies,
        Long parentId,
        Long postId) {
}
