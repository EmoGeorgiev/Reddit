package com.reddit.user.dto;

public record UpdatePasswordDto(
        String oldPassword,
        String newPassword) {
}
