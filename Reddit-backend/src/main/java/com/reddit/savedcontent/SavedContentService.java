package com.reddit.savedcontent;

import com.reddit.comment.Comment;
import com.reddit.content.Content;
import com.reddit.content.ContentService;
import com.reddit.exception.comment.CommentIsDeletedException;
import com.reddit.savedcontent.dto.SavedContentDto;
import com.reddit.savedcontent.dto.SavedDto;
import com.reddit.user.RedditUser;
import com.reddit.user.UserService;
import com.reddit.util.ErrorMessages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class SavedContentService {
    private final SavedContentRepository savedContentRepository;
    private final UserService userService;
    private final ContentService contentService;

    public SavedContentService(SavedContentRepository savedContentRepository, UserService userService, ContentService contentService) {
        this.savedContentRepository = savedContentRepository;
        this.userService = userService;
        this.contentService = contentService;
    }

    @Transactional(readOnly = true)
    public SavedDto getSavedByContentAndUser(Long contentId, Long userId) {
        Content content = contentService.getContentEntity(contentId);
        RedditUser user = userService.getUserEntity(userId);

        Optional<SavedContent> savedContentOptional = savedContentRepository.findByUserAndContent(user, content);

        return savedContentOptional
                .map(SavedContentMapper::savedContentToSavedDto)
                .orElseGet(() -> new SavedDto(userId, contentId, SavedType.NOT_SAVED));
    }

    @Transactional(readOnly = true)
    public Page<SavedContentDto> getSavedContentByUserId(Long userId, Pageable pageable) {
        return savedContentRepository
                .findByUserId(userId, pageable)
                .map(SavedContentMapper::savedContentToSavedContentDto);
    }

    public SavedDto toggleSavedContent(SavedDto savedDto) {
        RedditUser user = userService.getUserEntity(savedDto.userId());
        Content content = contentService.getContentEntity(savedDto.contentId());

        if (content instanceof Comment c) {
            if (c.isDeleted()) {
                throw new CommentIsDeletedException(ErrorMessages.COMMENT_IS_DELETED);
            }
        }

        Optional<SavedContent> optionalSavedContent = savedContentRepository.findByUserAndContent(user, content);

        return optionalSavedContent
                .map(this::removeSavedContent)
                .orElseGet(() -> addSavedContent(user, content));
    }

    private SavedDto addSavedContent(RedditUser user, Content content) {
        SavedContent savedContent = new SavedContent();
        savedContent.setCreated(LocalDateTime.now());
        savedContent.setUser(user);
        savedContent.setContent(content);

        SavedContent result = savedContentRepository.save(savedContent);

        return SavedContentMapper.savedContentToSavedDto(result);
    }

    private SavedDto removeSavedContent(SavedContent savedContent) {
        savedContentRepository.deleteById(savedContent.getId());

        return new SavedDto(
                savedContent.getUser().getId(),
                savedContent.getContent().getId(),
                SavedType.NOT_SAVED
        );
    }
}
