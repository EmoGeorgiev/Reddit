package com.reddit.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePasswordDto(
        @NotBlank(message = "Old password must not be blank")
        @Size(min = 3, max = 30, message = "Old password must be between 3 and 30 characters")
        String oldPassword,
        @NotBlank(message = "New password must not be blank")
        @Size(min = 3, max = 30, message = "New password must be between 3 and 30 characters")
        String newPassword) {
}
