package com.reddit.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<RedditUser, Long> {
    Optional<RedditUser> findByUsernameIgnoreCase(String username);
    Page<RedditUser> findByUsernameContainingIgnoreCase(String word, Pageable pageable);
    @Query("SELECT u FROM RedditUser u JOIN u.subscribedTo s WHERE s.title = :title")
    List<RedditUser> findBySubredditTitle(@Param("title") String subredditTitle, Sort sort);
    @Query("SELECT u FROM RedditUser u JOIN u.moderated s WHERE s.title = :title")
    List<RedditUser> findModeratorsBySubredditTitle(@Param("title") String subredditTitle, Sort sort);
}
