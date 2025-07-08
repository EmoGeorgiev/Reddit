package com.reddit.post.dto;

import com.reddit.subreddit.dto.SubredditDto;
import com.reddit.user.dto.UserDto;
import com.reddit.util.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record PostDto(
        Long id,
        UserDto user,
        LocalDateTime created,
        @NotBlank(message = "{text.required}")
        @Size(
                min = ValidationConstants.TEXT_MIN,
                max = ValidationConstants.TEXT_MAX,
                message = "{text.size}")
        String text,
        Integer score,
        Integer commentCount,
        @NotNull(message = "{subreddit.required}")
        SubredditDto subreddit,
        @NotBlank(message = "{title.required}")
        @Size(
                min = ValidationConstants.TITLE_MIN,
                max = ValidationConstants.TITLE_MAX,
                message = "{title.size}")
        String title) {
}
