package com.reddit.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePasswordDto(
        @NotBlank
        @Size(min = 3, max = 30, message = "Old password must be between 3 and 30 characters")
        String oldPassword,
        @NotBlank
        @Size(min = 3, max = 30, message = "New password must be between 3 and 30 characters")
        String newPassword) {
}
