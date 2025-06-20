package com.reddit.content;

import com.reddit.exception.ContentNotFoundException;
import com.reddit.util.ErrorMessages;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContentService {
    private final ContentRepository contentRepository;

    public ContentService(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    @Transactional(readOnly = true)
    public Content getContentEntity(Long id) {
        return contentRepository
                .findById(id)
                .orElseThrow(() -> new ContentNotFoundException(ErrorMessages.CONTENT_NOT_FOUND));
    }
}
