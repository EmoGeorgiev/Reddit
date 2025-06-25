package com.reddit.content;

import com.reddit.exception.content.ContentNotFoundException;
import com.reddit.util.ErrorMessages;
import org.springframework.stereotype.Service;

@Service
public class ContentService {
    private final ContentRepository contentRepository;

    public ContentService(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    public Content getContentEntity(Long id) {
        return contentRepository
                .findById(id)
                .orElseThrow(() -> new ContentNotFoundException(ErrorMessages.CONTENT_NOT_FOUND));
    }
}
