package com.reddit.user;

import com.reddit.content.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<RedditUser, Long> {
    Optional<RedditUser> findByUsername(String username);
    Page<RedditUser> findByUsernameContainingIgnoreCase(String word, Pageable pageable);
    @Query("SELECT c FROM RedditUser u JOIN u.saved c WHERE u.id = :userId")
    Page<Content> findSavedContentByUserId(@Param("userId") Long userId, Pageable pageable);
}
