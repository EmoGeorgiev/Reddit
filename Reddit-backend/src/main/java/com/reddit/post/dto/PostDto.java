package com.reddit.post.dto;

import com.reddit.util.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record PostDto(
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
        @NotNull(message = "{subredditId.required}")
        Long subredditId,
        @NotBlank(message = "{title.required}")
        @Size(
                min = ValidationConstants.TITLE_MIN,
                max = ValidationConstants.TITLE_MAX,
                message = "{title.size}")
        String title) {
}
