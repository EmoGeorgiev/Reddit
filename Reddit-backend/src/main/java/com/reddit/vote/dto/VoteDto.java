package com.reddit.vote.dto;

import com.reddit.content.dto.ContentDto;
import com.reddit.vote.VoteType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record VoteDto(
        Long id,
        LocalDateTime created,
        @NotNull(message = "User id cannot be null")
        Long userId,
        @NotNull(message = "ContentDto cannot be null")
        ContentDto contentDto,
        @NotNull(message = "VoteType cannot be null")
        VoteType voteType) {
}
