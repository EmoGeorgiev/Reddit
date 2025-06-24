package com.reddit.savedcontent;

import com.reddit.content.ContentMapper;
import com.reddit.savedcontent.dto.SavedContentDto;

public class SavedContentMapper {
    public static SavedContentDto savedContentToSavedContentDto(SavedContent savedContent) {
        if (savedContent == null) {
            return null;
        }

        return new SavedContentDto(
                savedContent.getId(),
                savedContent.getCreated(),
                savedContent.getUser().getId(),
                ContentMapper.contentToContentDto(savedContent.getContent()));
    }
}
