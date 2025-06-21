package com.reddit.user;

public record UpdatePasswordDto(
        String oldPassword,
        String newPassword) {
}
