package com.reddit.comment.dto;


import com.reddit.util.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;
public record CommentDto(
        Long id,
        Long userId,
        LocalDateTime created,
        @NotBlank(message = "{text.required}")
        @Size(
                min = ValidationConstants.TEXT_MIN,
                max = ValidationConstants.TEXT_MAX,
                message = "{text.size}")
        String text,
        Integer score,
        Boolean isDeleted,
        List<CommentDto> replies,
        Long parentId,
        @NotNull(message = "{postId.required}")
        Long postId) {
}
