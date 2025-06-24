package com.reddit.savedcontent;

import com.reddit.content.Content;
import com.reddit.user.RedditUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SavedContentRepository extends JpaRepository<SavedContent, Long> {
    Page<SavedContent> findByUserId(Long userId, Pageable pageable);
    Optional<SavedContent> findByUserAndContent(RedditUser user, Content content);
}
