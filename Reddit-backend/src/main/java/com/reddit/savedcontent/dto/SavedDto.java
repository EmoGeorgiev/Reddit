package com.reddit.savedcontent.dto;

import com.reddit.savedcontent.SavedType;
import jakarta.validation.constraints.NotNull;

public record SavedDto(
        @NotNull(message = "{userId.required}")
        Long userId,
        @NotNull(message = "{contentId.required}")
        Long contentId,
        SavedType savedType) {
}
