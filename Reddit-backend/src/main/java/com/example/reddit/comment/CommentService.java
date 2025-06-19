package com.example.reddit.comment;

import com.example.reddit.content.Content;
import com.example.reddit.exception.CommentCannotBeUpdatedException;
import com.example.reddit.exception.CommentNotFoundException;
import com.example.reddit.post.Post;
import com.example.reddit.post.PostService;
import com.example.reddit.user.RedditUser;
import com.example.reddit.user.UserService;
import com.example.reddit.util.ErrorMessages;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    @Transactional(readOnly = true)
    public Comment getCommentEntity(Long id) {
        return commentRepository
                .findById(id)
                .orElseThrow(() -> new CommentNotFoundException(ErrorMessages.COMMENT_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public CommentDto getComment(Long id) {
        return CommentMapper.commentToCommentDto(getCommentEntity(id));
    }

    public CommentDto addComment(CommentDto commentDto) {
        RedditUser user = userService.getUserEntity(commentDto.userId());
        Post post = postService.getPostEntity(commentDto.postId());
        Comment parent = commentDto.parentId() != null
                ? getCommentEntity(commentDto.parentId())
                : null;

        Comment comment = CommentMapper.commentDtoToComment(commentDto);
        comment.setUser(user);
        comment.setParent(parent);
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);

        return CommentMapper.commentToCommentDto(savedComment);
    }

    public CommentDto updateComment(CommentDto commentDto) {
        /*Comment comment = getCommentEntity(commentDto.id());

        if (comment.isDeleted()) {
            throw new CommentCannotBeUpdatedException(ErrorMessages.COMMENT_CANNOT_BE_UPDATED);
        }

        comment.setId(commentDto.id());


        Comment savedComment = commentRepository.save(comment);

        return CommentMapper.commentToCommentDto(savedComment);*/
        throw new UnsupportedOperationException();
    }

    public CommentDto deleteComment(Long id) {
        Comment comment = getCommentEntity(id);

        if (comment.isDeleted()) {
            return CommentMapper.commentToCommentDto(comment);
        }

        comment.setText(Comment.DELETED_TEXT);
        comment.setScore(Content.INITIAL_SCORE);
        comment.getVotes().clear();
        comment.setDeleted(true);

        Comment savedComment = commentRepository.save(comment);

        return CommentMapper.commentToCommentDto(savedComment);
    }
}
