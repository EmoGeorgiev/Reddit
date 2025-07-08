package com.reddit.subreddit;

import com.reddit.subreddit.dto.SubredditDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
    Optional<Subreddit> findByTitleIgnoreCase(String title);
    List<Subreddit> findByUsers_Id(Long userId, Sort sort);
    List<Subreddit> findByModerators_Id(Long moderatorId, Sort sort);
    Page<Subreddit> findByTitleContainingIgnoreCase(String word, Pageable pageable);
}
