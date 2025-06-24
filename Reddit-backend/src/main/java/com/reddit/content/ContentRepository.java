package com.reddit.content;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContentRepository extends JpaRepository<Content, Long> {
    @Query("SELECT c FROM Content c JOIN FETCH c.savedBy u WHERE u.id = :userId")
    Page<Content> findSavedContentByUserId(@Param("userId") Long userId, Pageable pageable);
}
