package com.reddit.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<RedditUser, Long> {
    Optional<RedditUser> findByUsername(String username);
    List<RedditUser> findByUsernameContainingIgnoreCase(String word);
}
