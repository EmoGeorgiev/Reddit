package com.example.reddit.vote;

public record VoteDto(
        Long id,
        Long userId,
        Long contentId,
        VoteType voteType) {
}
