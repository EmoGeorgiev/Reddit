package com.reddit.authentication.dto;

public record LoginResponseDto(
        Long id,
        String username,
        String token) {
}
