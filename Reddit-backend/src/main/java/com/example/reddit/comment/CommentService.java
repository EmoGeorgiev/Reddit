package com.example.reddit.comment;

import com.example.reddit.content.Content;
import com.example.reddit.exception.CommentNotFoundException;
import com.example.reddit.util.ErrorMessages;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional(readOnly = true)
    public CommentDto getComment(Long id) {
        return commentRepository
                .findById(id)
                .map(CommentMapper::commentToCommentDto)
                .orElseThrow(() -> new CommentNotFoundException(ErrorMessages.COMMENT_NOT_FOUND));
    }

    public CommentDto addComment(CommentDto commentDto) {
        return null;
    }

    public CommentDto updateComment(CommentDto commentDto) {
        return null;
    }

    public CommentDto deleteComment(Long id) {
        Comment comment = commentRepository
                .findById(id)
                .orElseThrow(() -> new CommentNotFoundException(ErrorMessages.COMMENT_NOT_FOUND));

        comment.setText(Comment.DELETED_TEXT);
        comment.setScore(Content.INITIAL_SCORE);
        comment.getVotes().clear();
        comment.setDeleted(true);

        Comment savedComment = commentRepository.save(comment);

        return CommentMapper.commentToCommentDto(savedComment);
    }
}
