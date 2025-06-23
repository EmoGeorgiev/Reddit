package com.reddit.post;

import java.time.LocalDateTime;

public record PostDto(
        Long id,
        Long userId,
        LocalDateTime created,
        String text,
        int score,
        Long subredditId,
        String title) {
}
