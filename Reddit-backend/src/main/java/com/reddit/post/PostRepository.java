package com.reddit.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findBySubredditId(Long subredditId, Pageable pageable);
    Page<Post> findByUserId(Long userId, Pageable pageable);
}
