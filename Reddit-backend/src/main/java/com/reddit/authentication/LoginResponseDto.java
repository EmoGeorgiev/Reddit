package com.reddit.authentication;

public record LoginResponseDto(
        Long id,
        String username,
        String token) {
}
