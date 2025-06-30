package com.reddit.comment;

import com.reddit.comment.dto.CommentDto;
import com.reddit.exception.comment.CommentIsDeletedException;
import com.reddit.exception.comment.CommentNotFoundException;
import com.reddit.exception.content.ContentUpdateNotAllowedException;
import com.reddit.exception.subreddit.MissingModeratorPrivilegesException;
import com.reddit.post.Post;
import com.reddit.post.PostService;
import com.reddit.subreddit.Subreddit;
import com.reddit.user.RedditUser;
import com.reddit.user.UserService;
import com.reddit.util.ErrorMessages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Transactional(readOnly = true)
    public Page<CommentDto> getCommentsByPostId(Long postId, Pageable pageable) {
        return commentRepository
                .findByPostId(postId, pageable)
                .map(CommentMapper::commentToCommentDto);
    }

    @Transactional(readOnly = true)
    public Page<CommentDto> getCommentsByUserId(Long userId, Pageable pageable) {
        return commentRepository
                .findByUserId(userId, pageable)
                .map(CommentMapper::commentToCommentDto);
    }

    public CommentDto addComment(CommentDto commentDto) {
        RedditUser user = userService.getUserEntity(commentDto.userId());
        Post post = postService.getPostEntity(commentDto.postId());
        Comment parent = commentDto.parentId() != null
                ? getCommentEntity(commentDto.parentId())
                : null;

        Comment comment = CommentMapper.commentDtoToComment(commentDto);
        comment.setCreated(LocalDateTime.now());
        comment.setUser(user);
        comment.setParent(parent);
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);

        return CommentMapper.commentToCommentDto(savedComment);
    }

    public CommentDto updateComment(Long id, CommentDto commentDto) {
        Comment comment = getCommentEntity(id);

        if (!Objects.equals(comment.getUser().getId(), commentDto.userId())) {
            throw new ContentUpdateNotAllowedException(ErrorMessages.CONTENT_UPDATE_NOT_ALLOWED);
        }
        if (comment.isDeleted()) {
            throw new CommentIsDeletedException(ErrorMessages.COMMENT_IS_DELETED);
        }

        comment.setText(commentDto.text());

        return CommentMapper.commentToCommentDto(comment);
    }

    public CommentDto deleteComment(Long commentId, Long userId) {
        Comment comment = getCommentEntity(commentId);
        RedditUser user = userService.getUserEntity(userId);
        Subreddit subreddit = comment.getPost().getSubreddit();

        if (!comment.getUser().equals(user)) {
            if (!subreddit.getModerators().contains(user)) {
                throw new MissingModeratorPrivilegesException(ErrorMessages.MISSING_MODERATOR_PRIVILEGES);
            }
        }

        if (comment.isDeleted()) {
            return CommentMapper.commentToCommentDto(comment);
        }

        comment.setText(Comment.DELETED_TEXT);
        comment.setDeleted(true);

        return CommentMapper.commentToCommentDto(comment);
    }
}
