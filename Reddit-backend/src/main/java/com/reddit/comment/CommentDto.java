package com.reddit.comment;


import java.time.LocalDateTime;
import java.util.List;
public record CommentDto(
        Long id,
        Long userId,
        LocalDateTime created,
        String text,
        int score,
        boolean isDeleted,
        List<CommentDto> replies,
        Long parentId,
        Long postId) {
}
