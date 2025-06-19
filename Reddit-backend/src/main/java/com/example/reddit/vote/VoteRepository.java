package com.example.reddit.vote;

import com.example.reddit.content.Content;
import com.example.reddit.user.RedditUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByUserAndContent(RedditUser user, Content content);
}
