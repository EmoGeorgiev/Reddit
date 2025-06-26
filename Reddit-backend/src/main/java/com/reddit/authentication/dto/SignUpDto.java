package com.reddit.authentication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpDto(
        @NotBlank(message = "Username must not be blank")
        @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
        String username,
        @NotBlank(message = "Password must not be blank")
        @Size(min = 3, max = 30, message = "Password must be between 3 and 30 characters")
        String password) {
}
