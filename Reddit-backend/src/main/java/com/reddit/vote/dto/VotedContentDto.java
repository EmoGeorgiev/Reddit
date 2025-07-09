package com.reddit.vote.dto;

import com.reddit.content.dto.ContentDto;
import com.reddit.vote.VoteType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record VotedContentDto(
        Long id,
        LocalDateTime created,
        @NotNull(message = "{userId.required}")
        Long userId,
        @NotNull(message = "{contentDto.required}")
        ContentDto contentDto,
        @NotNull(message = "{voteType.required}")
        VoteType voteType) {
}
