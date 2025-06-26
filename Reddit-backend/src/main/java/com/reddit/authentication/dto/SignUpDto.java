package com.reddit.authentication.dto;

import com.reddit.util.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpDto(
        @NotBlank(message = "{username.required}")
        @Size(
                min = ValidationConstants.USERNAME_MIN,
                max = ValidationConstants.USERNAME_MAX,
                message = "{username.size}")
        String username,
        @NotBlank(message = "{password.required}")
        @Size(
                min = ValidationConstants.PASSWORD_MIN,
                max = ValidationConstants.PASSWORD_MAX,
                message = "{password.size}")
        String password) {
}
