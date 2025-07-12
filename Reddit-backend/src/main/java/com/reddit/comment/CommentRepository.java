package com.reddit.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByUserIdAndIsDeletedFalse(Long userId, Pageable pageable);
    Page<Comment> findByPostIdAndParentIsNull(Long postId, Pageable pageable);
}
