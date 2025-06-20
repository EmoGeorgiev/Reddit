package com.example.reddit.authentication;

public record LoginResponseDto(
        Long id,
        String username,
        String token) {
}
