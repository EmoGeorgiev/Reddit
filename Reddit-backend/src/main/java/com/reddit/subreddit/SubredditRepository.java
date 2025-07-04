package com.reddit.subreddit;

import com.reddit.subreddit.dto.SubredditDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
    Optional<Subreddit> findByTitleIgnoreCase(String title);
    Set<Subreddit> findByUsers_Id(Long userId);
    Set<Subreddit> findByModerators_Id(Long moderatorId);
    Page<Subreddit> findByTitleContainingIgnoreCase(String word, Pageable pageable);
}
