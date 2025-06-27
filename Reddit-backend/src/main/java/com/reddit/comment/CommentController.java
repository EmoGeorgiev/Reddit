package com.reddit.comment;

import com.reddit.comment.dto.CommentDto;
import com.reddit.util.PaginationConstants;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getComment(@PathVariable Long id) {
        CommentDto commentDto = commentService.getComment(id);

        return ResponseEntity
                .ok()
                .body(commentDto);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Page<CommentDto>> getCommentsByPostId(
            @PathVariable Long postId,
            @PageableDefault(
                    size = PaginationConstants.COMMENT_BY_POST_ID_SIZE,
                    sort = PaginationConstants.COMMENT_BY_POST_ID_SORT,
                    direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CommentDto> comments = commentService.getCommentsByPostId(postId, pageable);

        return ResponseEntity
                .ok()
                .body(comments);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Page<CommentDto>> getCommentsByUserId(
            @PathVariable Long userId,
            @PageableDefault(
                    size = PaginationConstants.COMMENT_BY_USER_ID_SIZE,
                    sort = PaginationConstants.COMMENT_BY_USER_ID_SORT,
                    direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CommentDto> comments = commentService.getCommentsByUserId(userId, pageable);

        return ResponseEntity
                .ok()
                .body(comments);
    }

    @PostMapping
    public ResponseEntity<CommentDto> addComment(@RequestBody @Valid CommentDto commentDto) {
        CommentDto resultCommentDto = commentService.addComment(commentDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resultCommentDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long id, @RequestBody @Valid CommentDto commentDto) {
        CommentDto updatedCommentDto = commentService.updateComment(id, commentDto);
        return ResponseEntity
                .ok()
                .body(updatedCommentDto);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentDto> deleteComment(@PathVariable Long commentId, @RequestParam Long userId) {
        CommentDto deletedComment = commentService.deleteComment(commentId, userId);
        return ResponseEntity
                .ok()
                .body(deletedComment);
    }
}
