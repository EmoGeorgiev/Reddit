package com.reddit.user.dto;

import com.reddit.util.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserDeleteDto(
        @NotNull(message = "{userId.required}")
        Long id,
        @NotBlank(message = "{password.required}")
        @Size(
                min = ValidationConstants.PASSWORD_MIN,
                max = ValidationConstants.PASSWORD_MAX,
                message = "{password.size}")
        String password) {
}
