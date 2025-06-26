package com.reddit.comment.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;
public record CommentDto(
        Long id,
        Long userId,
        LocalDateTime created,
        @NotBlank(message = "Text must not be blank")
        @Size(min = 1, max = 5000, message = "Text must be between 1 and 5000 characters")
        String text,
        Integer score,
        Boolean isDeleted,
        List<CommentDto> replies,
        Long parentId,
        @NotNull(message = "Post id cannot be null")
        Long postId) {
}
