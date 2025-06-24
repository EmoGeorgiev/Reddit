package com.reddit.comment;

import com.reddit.comment.dto.CommentDto;
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
            @PageableDefault(size = 200, sort = "score", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CommentDto> comments = commentService.getCommentsByPostId(postId, pageable);

        return ResponseEntity
                .ok()
                .body(comments);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Page<CommentDto>> getCommentsByUserId(
            @PathVariable Long userId,
            @PageableDefault(size = 25, sort = "created", direction = Sort.Direction.DESC) Pageable pageable
    ) {
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

    @DeleteMapping("/{id}")
    public ResponseEntity<CommentDto> deleteComment(@PathVariable Long id) {
        CommentDto deletedComment = commentService.deleteComment(id);
        return ResponseEntity
                .ok()
                .body(deletedComment);
    }
}
