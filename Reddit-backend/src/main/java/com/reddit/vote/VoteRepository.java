package com.reddit.vote;

import com.reddit.content.Content;
import com.reddit.user.RedditUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByUserAndContent(RedditUser user, Content content);
    Page<Vote> findByUserIdAndVoteType(Long userId, VoteType voteType, Pageable pageable);
}
