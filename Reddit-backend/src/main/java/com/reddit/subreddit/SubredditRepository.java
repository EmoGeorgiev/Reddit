package com.reddit.subreddit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
    Optional<Subreddit> findByTitle(String title);
    @Query("SELECT s FROM Subreddit s JOIN s.users u WHERE u.id = :userId")
    List<Subreddit> findByUsers_Id(@Param("userId") Long userId);
    @Query("SELECT s FROM Subreddit s WHERE LOWER(s.title) LIKE LOWER(CONCAT('%', :word, '%'))")
    List<Subreddit> findByTitleContainingIgnoreCase(@Param("word") String word);
}
