package com.reddit.user.dto;

import com.reddit.util.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePasswordDto(
        @NotBlank(message = "{password.required}")
        @Size(
                min = ValidationConstants.PASSWORD_MIN,
                max = ValidationConstants.PASSWORD_MAX,
                message = "{password.size}")
        String oldPassword,
        @NotBlank(message = "{password.required}")
        @Size(
                min = ValidationConstants.PASSWORD_MIN,
                max = ValidationConstants.PASSWORD_MAX,
                message = "{password.size}")
        String newPassword) {
}
