package com.reddit.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByTitleContainingIgnoreCase(String word, Pageable pageable);
    Page<Post> findBySubredditId(Long subredditId, Pageable pageable);
    Page<Post> findByUserId(Long userId, Pageable pageable);
    @Query("""
        SELECT p
        FROM Post p
            JOIN p.subreddit s
            JOIN s.users u
        WHERE u.id = :userId
    """)
    Page<Post> findPostsByUserSubscriptions(@Param("userId") Long userId, Pageable pageable);
}
