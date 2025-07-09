package com.reddit.vote.dto;

import com.reddit.vote.VoteType;
import jakarta.validation.constraints.NotNull;

public record VoteDto(
        Long userId,
        @NotNull(message = "{contentId.required}")
        Long contentId,
        VoteType voteType,
        Integer score) {
}
