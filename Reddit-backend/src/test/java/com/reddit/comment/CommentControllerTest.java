package com.reddit.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reddit.comment.dto.CommentDto;
import com.reddit.exception.comment.CommentIsDeletedException;
import com.reddit.exception.comment.CommentNotFoundException;
import com.reddit.exception.content.ContentUpdateNotAllowedException;
import com.reddit.exception.subreddit.MissingModeratorPrivilegesException;
import com.reddit.user.dto.UserDto;
import com.reddit.util.ErrorMessages;
import com.reddit.util.PaginationConstants;
import com.reddit.util.ValidationConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;

import static com.reddit.util.TestUtils.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CommentControllerTest {
    private static final String BASE_URL = "/api/comments";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MessageSource messageSource;
    @MockitoBean
    private CommentService commentService;
    private final Long id = 1L;
    private final UserDto userDto = new UserDto(id, "user");
    private final CommentDto commentDto = new CommentDto(id, userDto, null, "text", Comment.INITIAL_SCORE, false, new ArrayList<>(), null, id);

    @Test
    public void shouldReturnNotFoundForInvalidId() throws Exception {
        Long invalidId = -1L;
        when(commentService.getComment(invalidId))
                .thenThrow(new CommentNotFoundException(ErrorMessages.COMMENT_NOT_FOUND));

        mockMvc
                .perform(get(BASE_URL + "/" + invalidId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(ErrorMessages.COMMENT_NOT_FOUND));

        verify(commentService)
                .getComment(invalidId);
    }

    @Test
    public void shouldReturnCommentForValidId() throws Exception {
        when(commentService.getComment(id))
                .thenReturn(commentDto);

        mockMvc
                .perform(get(BASE_URL + "/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(commentDtoMatchers("$.", commentDto));

        verify(commentService)
                .getComment(id);
    }

    @Test
    public void shouldReturnEmptyPageWhenPostDoesNotHaveComments() throws Exception {
        int expectedTotalPages = 1;
        int count = 0;
        Pageable pageable = getPageable(PaginationConstants.COMMENT_BY_POST_ID_SIZE, PaginationConstants.COMMENT_BY_POST_ID_SORT);
        Page<CommentDto> emptyPage = getPage(commentDto, count, pageable);

        when(commentService.getCommentsByPostId(id, pageable))
                .thenReturn(emptyPage);

        mockMvc
                .perform(get(BASE_URL + "/posts/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.totalElements").value(count))
                .andExpect(jsonPath("$.totalPages").value(expectedTotalPages));

        verify(commentService)
                .getCommentsByPostId(id, pageable);
    }

    @Test
    public void shouldReturnPageWithOneCommentWhenPostHasOneComment() throws Exception {
        int expectedTotalPages = 1;
        int count = 1;
        Pageable pageable = getPageable(PaginationConstants.COMMENT_BY_POST_ID_SIZE, PaginationConstants.COMMENT_BY_POST_ID_SORT);
        Page<CommentDto> pageWithOneElement = getPage(commentDto, count, pageable);

        when(commentService.getCommentsByPostId(id, pageable))
                .thenReturn(pageWithOneElement);

        mockMvc
                .perform(get(BASE_URL + "/posts/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalElements").value(count))
                .andExpect(jsonPath("$.totalPages").value(expectedTotalPages))
                .andExpectAll(commentDtoMatchers("$.content[0].", commentDto));

        verify(commentService)
                .getCommentsByPostId(id, pageable);
    }

    @Test
    public void shouldReturnEmptyPageWhenUserDoesNotHaveComments() throws Exception {
        int expectedTotalPages = 1;
        int count = 0;
        Pageable pageable = getPageable(PaginationConstants.COMMENT_BY_USER_ID_SIZE, PaginationConstants.COMMENT_BY_USER_ID_SORT);
        Page<CommentDto> emptyPage = getPage(commentDto, count, pageable);

        when(commentService.getCommentsByUserId(id, pageable))
                .thenReturn(emptyPage);

        mockMvc
                .perform(get(BASE_URL + "/users/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.totalElements").value(count))
                .andExpect(jsonPath("$.totalPages").value(expectedTotalPages));

        verify(commentService)
                .getCommentsByUserId(id, pageable);
    }

    @Test
    public void shouldReturnPageWithOneCommentWhenUserHasOneComment() throws Exception {
        int expectedTotalPages = 1;
        int count = 1;
        Pageable pageable = getPageable(PaginationConstants.COMMENT_BY_USER_ID_SIZE, PaginationConstants.COMMENT_BY_USER_ID_SORT);
        Page<CommentDto> pageWithOneElement = getPage(commentDto, count, pageable);

        when(commentService.getCommentsByUserId(id, pageable))
                .thenReturn(pageWithOneElement);

        mockMvc
                .perform(get(BASE_URL + "/users/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalElements").value(count))
                .andExpect(jsonPath("$.totalPages").value(expectedTotalPages))
                .andExpectAll(commentDtoMatchers("$.content[0].", commentDto));

        verify(commentService)
                .getCommentsByUserId(id, pageable);
    }

    @Test
    public void shouldReturnBadRequestForBlankTextWhenCreatingComment() throws Exception {
        CommentDto blankTextCommentDto = new CommentDto(id, userDto, null, BLANK_STRING, Comment.INITIAL_SCORE, false, new ArrayList<>(), null, id);

        String expectedMessage = messageSource.getMessage("text.required", null, LocaleContextHolder.getLocale());

        mockMvc
                .perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(blankTextCommentDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.description").value(expectedMessage));
    }

    @Test
    public void shouldReturnBadRequestForNotValidTextWhenCreatingComment() throws Exception {
        String text = getStringWithFixedLength(ValidationConstants.TEXT_MAX + 1);

        CommentDto overMaxTextSizeCommentDto = new CommentDto(id, userDto, null, text, Comment.INITIAL_SCORE, false, new ArrayList<>(), null, id);

        Object[] args = getArgs(ValidationConstants.TEXT_MIN, ValidationConstants.TEXT_MAX);
        String expectedMessage = messageSource.getMessage(
                "text.size.test",
                args,
                LocaleContextHolder.getLocale());

        mockMvc
                .perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(overMaxTextSizeCommentDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.description").value(expectedMessage));
    }

    @Test
    public void shouldReturnBadRequestForNullPostIdWhenCreatingComment() throws Exception {
        CommentDto nullPostIdCommentDto = new CommentDto(id, userDto, null, "text", Comment.INITIAL_SCORE, false, new ArrayList<>(), null, null);

        String expectedMessage = messageSource.getMessage(
                "postId.required",
                null,
                LocaleContextHolder.getLocale());

        mockMvc
                .perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nullPostIdCommentDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.postId").value(expectedMessage));
    }

    @Test
    public void shouldReturnCommentResultWhenCreatingComment() throws Exception {
        when(commentService.addComment(commentDto))
                .thenReturn(commentDto);

        mockMvc
                .perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDto)))
                .andExpect(status().isCreated())
                .andExpectAll(commentDtoMatchers("$.", commentDto));

        verify(commentService)
                .addComment(commentDto);
    }

    @Test
    public void shouldReturnConflictForNonMatchingUserIdAndCreatorIdWhenUpdatingComment() throws Exception {
        when(commentService.updateComment(id, commentDto))
                .thenThrow(new ContentUpdateNotAllowedException(ErrorMessages.CONTENT_UPDATE_NOT_ALLOWED));

        mockMvc
                .perform(put(BASE_URL + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(ErrorMessages.CONTENT_UPDATE_NOT_ALLOWED));

        verify(commentService)
                .updateComment(id, commentDto);
    }

    @Test
    public void shouldReturnConflictForDeletedCommentWhenUpdatingComment() throws Exception {
        when(commentService.updateComment(id, commentDto))
                .thenThrow(new CommentIsDeletedException(ErrorMessages.COMMENT_IS_DELETED));

        mockMvc
                .perform(put(BASE_URL + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(ErrorMessages.COMMENT_IS_DELETED));

        verify(commentService)
                .updateComment(id, commentDto);
    }

    @Test
    public void shouldReturnUpdatedTitleCommentWhenUpdatingComment() throws Exception {
        when(commentService.updateComment(id, commentDto))
                .thenReturn(commentDto);

        mockMvc
                .perform(put(BASE_URL + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDto)))
                .andExpect(status().isOk())
                .andExpectAll(commentDtoMatchers("$.", commentDto));

        verify(commentService)
                .updateComment(id, commentDto);
    }

    @Test
    public void shouldReturnForbiddenForMissingModeratorPrivilegesWhenDeletingComment() throws Exception {
        when(commentService.deleteComment(id, id))
                .thenThrow(new MissingModeratorPrivilegesException(ErrorMessages.MISSING_MODERATOR_PRIVILEGES));

        mockMvc
                .perform(delete(BASE_URL + "/" + id + "?userId=" + id))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(ErrorMessages.MISSING_MODERATOR_PRIVILEGES));

        verify(commentService)
                .deleteComment(id, id);
    }

    @Test
    public void shouldReturnDeletedCommentCommentWhenDeletingComment() throws Exception {
        CommentDto deletedCommentDto = new CommentDto(id, userDto, null, Comment.DELETED_TEXT, Comment.INITIAL_SCORE, true, null, null, id);

        when(commentService.deleteComment(id, id))
                .thenReturn(deletedCommentDto);

        mockMvc
                .perform(delete(BASE_URL + "/" + id + "?userId=" + id))
                .andExpect(status().isOk())
                .andExpectAll(commentDtoMatchers("$.", deletedCommentDto));

        verify(commentService)
                .deleteComment(id, id);
    }

    private ResultMatcher[] commentDtoMatchers(String prefix, CommentDto dto) {
        return new ResultMatcher[] {
                jsonPath(prefix + "id").value(dto.id()),
                jsonPath(prefix + "user.id").value(dto.user().id()),
                jsonPath(prefix + "user.username").value(dto.user().username()),
                jsonPath(prefix + "created").value(dto.created()),
                jsonPath(prefix + "description").value(dto.text()),
                jsonPath(prefix + "score").value(dto.score()),
                jsonPath(prefix + "isDeleted").value(dto.isDeleted()),
                jsonPath(prefix + "replies").value(dto.replies()),
                jsonPath(prefix + "parentId").value(dto.parentId()),
                jsonPath(prefix + "postId").value(dto.postId())
        };
    }
}
