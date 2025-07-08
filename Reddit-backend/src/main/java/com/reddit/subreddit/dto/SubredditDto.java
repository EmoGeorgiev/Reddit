package com.reddit.subreddit.dto;

import com.reddit.util.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record SubredditDto(
        Long id,
        @NotBlank(message = "{title.required}")
        @Size(
                min = ValidationConstants.TITLE_MIN,
                max = ValidationConstants.TITLE_MAX,
                message = "{title.size}")
        String title) {
}
