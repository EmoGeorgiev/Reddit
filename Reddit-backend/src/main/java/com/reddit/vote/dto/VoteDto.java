package com.reddit.vote.dto;

import com.reddit.content.dto.ContentDto;
import com.reddit.vote.VoteType;

import java.time.LocalDateTime;

public record VoteDto(
        Long id,
        LocalDateTime created,
        Long userId,
        ContentDto contentDto,
        VoteType voteType) {
}
