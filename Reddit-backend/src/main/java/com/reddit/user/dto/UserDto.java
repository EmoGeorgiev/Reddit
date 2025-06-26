package com.reddit.user.dto;

import com.reddit.util.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDto(
        Long id,
        @NotBlank(message = "{username.required}")
        @Size(
                min = ValidationConstants.USERNAME_MIN,
                max = ValidationConstants.USERNAME_MAX,
                message = "{username.size}")
        String username) {
}
