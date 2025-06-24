package com.reddit.vote.dto;

import com.reddit.vote.VoteType;

public record VoteDto(
        Long id,
        Long userId,
        Long contentId,
        VoteType voteType) {
}
