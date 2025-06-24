package com.reddit.content;

import com.reddit.content.dto.ContentDto;
import com.reddit.exception.content.ContentNotFoundException;
import com.reddit.user.RedditUser;
import com.reddit.user.UserService;
import com.reddit.util.ErrorMessages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void toggleSaveContent(Long contentId, Long userId) {
        Content content = getContentEntity(contentId);
        RedditUser user = userService.getUserEntity(userId);

        if (!user.getSaved().contains(content)) {
            user.getSaved().add(content);
            content.getSavedBy().add(user);
        } else {
            user.getSaved().remove(content);
            content.getSavedBy().remove(user);
        }
    }
}
