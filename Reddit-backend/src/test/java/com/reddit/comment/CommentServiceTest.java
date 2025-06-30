package com.reddit.comment;

import com.reddit.comment.dto.CommentDto;
import com.reddit.exception.comment.CommentNotFoundException;
import com.reddit.post.Post;
import com.reddit.post.PostService;
import com.reddit.user.RedditUser;
import com.reddit.user.UserService;
import com.reddit.util.ErrorMessages;
import com.reddit.util.PaginationConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.reddit.util.TestUtils.getPage;
import static com.reddit.util.TestUtils.getPageable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private UserService userService;
    @Mock
    private PostService postService;
    @InjectMocks
    private CommentService commentService;

    private final Long id = 1L;
    private final Long invalidId = -1L;
    private RedditUser user;
    private Post post;
    private Comment firstComment;
    private CommentDto firstCommentDto;
    private final Pageable pageable = getPageable(PaginationConstants.COMMENT_BY_POST_ID_SIZE, PaginationConstants.COMMENT_BY_POST_ID_SORT);

    @BeforeEach
    void setUp() {
        user = new RedditUser();
        user.setId(id);
        user.setUsername("username");
        user.setPassword("encoded password");
        user.getComments().add(firstComment);

        post = new Post();
        post.setId(id);
        post.setUser(user);
        post.setCreated(LocalDateTime.now());
        post.setText("post text");
        post.setTitle("post title");

        firstComment = new Comment();
        firstComment.setId(id);
        firstComment.setUser(user);
        firstComment.setCreated(LocalDateTime.now());
        firstComment.setText("comment text");
        firstComment.setPost(post);

        firstCommentDto = CommentMapper.commentToCommentDto(firstComment);
    }

    @Test
    public void shouldThrowCommentNotFoundExceptionForInvalidIdWhenGettingCommentEntity() {
        when(commentRepository.findById(invalidId))
                .thenThrow(new CommentNotFoundException(ErrorMessages.COMMENT_NOT_FOUND));

        assertThrows(CommentNotFoundException.class,
                () -> commentService.getCommentEntity(invalidId));

        verify(commentRepository)
                .findById(invalidId);
    }

    @Test
    public void shouldReturnCommentForValidIdWhenGettingCommentEntity() {
        when(commentRepository.findById(id))
                .thenReturn(Optional.of(firstComment));

        Comment result = commentService.getCommentEntity(id);

        assertAll(commentAssertions(firstComment, result));

        verify(commentRepository)
                .findById(id);
    }

    @Test
    public void shouldReturnCommentDtoForValidIdWhenGettingComment() {
        when(commentRepository.findById(id))
                .thenReturn(Optional.of(firstComment));

        CommentDto result = commentService.getComment(id);

        assertAll(commentDtoAssertions(firstCommentDto, result));

        verify(commentRepository)
                .findById(id);
    }

    @Test
    public void shouldReturnEmptyPageForPostWithNoCommentsWhenGettingCommentsByPostId() {
        int expectedTotalPages = 1;
        int count = 0;

        Page<Comment> emptyPage = getPage(firstComment, count, pageable);

        when(commentRepository.findByPostId(id, pageable))
                .thenReturn(emptyPage);

        Page<CommentDto> result = commentService.getCommentsByPostId(id, pageable);

        assertEquals(expectedTotalPages, result.getTotalPages());
        assertEquals(count, result.getTotalElements());
        assertTrue(result.isEmpty());

        verify(commentRepository)
                .findByPostId(id, pageable);
    }

    @Test
    public void shouldReturnPageWithOneCommentForPostWithOneCommentWhenGettingCommentsByPostId() {
        int expectedTotalPages = 1;
        int count = 1;

        Page<Comment> pageWithOneComment = getPage(firstComment, count, pageable);

        when(commentRepository.findByPostId(id, pageable))
                .thenReturn(pageWithOneComment);

        Page<CommentDto> result = commentService.getCommentsByPostId(id, pageable);

        assertEquals(expectedTotalPages, result.getTotalPages());
        assertEquals(count, result.getTotalElements());

        CommentDto resultCommentDto = result.getContent().get(0);

        assertAll(commentDtoAssertions(firstCommentDto, resultCommentDto));

        verify(commentRepository)
                .findByPostId(id, pageable);
    }

    private Executable[] commentAssertions(Comment expected, Comment actual) {
        return new Executable[] {
                () -> assertNotNull(actual),
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getUser(), actual.getUser()),
                () -> assertEquals(expected.getCreated(), actual.getCreated()),
                () -> assertEquals(expected.getText(), actual.getText()),
                () -> assertEquals(expected.getScore(), actual.getScore()),
                () -> assertEquals(expected.getPost(), actual.getPost()),
                () -> assertEquals(expected.isDeleted(), actual.isDeleted()),
                () -> assertEquals(expected.getParent(), actual.getParent()),
        };
    }

    private Executable[] commentDtoAssertions(CommentDto expected, CommentDto actual) {
        return new Executable[] {
                () -> assertNotNull(actual),
                () -> assertEquals(expected.id(), actual.id()),
                () -> assertEquals(expected.userId(), actual.userId()),
                () -> assertEquals(expected.created(), actual.created()),
                () -> assertEquals(expected.text(), actual.text()),
                () -> assertEquals(expected.score(), actual.score()),
                () -> assertEquals(expected.isDeleted(), actual.isDeleted()),
                () -> assertEquals(expected.parentId(), actual.parentId()),
                () -> assertEquals(expected.postId(), actual.postId())
        };
    }
}
