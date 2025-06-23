package com.reddit.subreddit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
    Optional<Subreddit> findByTitle(String title);
    List<Subreddit> findByUsers_Id(Long userId);
    Page<Subreddit> findByTitleContainingIgnoreCase(String word, Pageable pageable);
}
