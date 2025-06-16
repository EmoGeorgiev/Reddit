package com.example.reddit.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<RedditUser, Long> {
}
