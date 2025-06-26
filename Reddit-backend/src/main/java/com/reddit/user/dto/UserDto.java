package com.reddit.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDto(
        Long id,
        @NotBlank(message = "Username must not be blank")
        @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
        String username) {
}
