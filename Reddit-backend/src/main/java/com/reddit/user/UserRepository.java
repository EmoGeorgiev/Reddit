package com.reddit.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<RedditUser, Long> {
    Optional<RedditUser> findByUsernameIgnoreCase(String username);
    Page<RedditUser> findByUsernameContainingIgnoreCase(String word, Pageable pageable);
}
