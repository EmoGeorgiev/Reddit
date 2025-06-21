package com.reddit.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<RedditUser, Long> {
    Optional<RedditUser> findByUsername(String username);
    @Query("SELECT u FROM RedditUser u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :word, '%'))")
    List<RedditUser> findByUsernameContainingIgnoreCase(@Param("word") String word);
}
