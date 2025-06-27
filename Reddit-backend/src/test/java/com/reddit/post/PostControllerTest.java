package com.reddit.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reddit.exception.content.ContentUpdateNotAllowedException;
import com.reddit.exception.post.PostNotFoundException;
import com.reddit.exception.subreddit.MissingModeratorPrivilegesException;
import com.reddit.post.dto.PostDto;
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
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PostControllerTest {
    private static final String BASE_URL = "/api/posts";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MessageSource messageSource;
    @MockitoBean
    private PostService postService;
    private final String blankString = "         ";
    private final Long id = 1L;
    private final PostDto postDto = new PostDto(id, id, null, "text", Post.INITIAL_SCORE, id, "title");

    @Test
    public void shouldReturnNotFoundForInvalidIdWhenGettingPost() throws Exception {
        Long invalidId = -1L;

        when(postService.getPost(invalidId))
                .thenThrow(new PostNotFoundException(ErrorMessages.POST_NOT_FOUND));

        mockMvc
                .perform(get(BASE_URL + "/" + invalidId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(ErrorMessages.POST_NOT_FOUND));

        verify(postService)
                .getPost(invalidId);
    }

    @Test
    public void shouldReturnPostForValidIdWhenGettingPost() throws Exception {
        when(postService.getPost(id))
                .thenReturn(postDto);

        mockMvc
                .perform(get(BASE_URL + "/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(postDtoMatchers("$.", postDto));

        verify(postService)
                .getPost(id);
    }

    @Test
    public void shouldReturnEmptyPageForNoPostWhenGettingPosts() throws Exception {
        int count = 0;
        Pageable pageable = defaultPageable();
        Page<PostDto> emptyPage = postPage(count, pageable);

        when(postService.getPosts(pageable))
                .thenReturn(emptyPage);

        mockMvc
                .perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.totalElements").value(count))
                .andExpect(jsonPath("$.totalPages").value(1));

        verify(postService)
                .getPosts(pageable);
    }

    @Test
    public void shouldReturnPageWithOnePostForOnePostWhenGettingPosts() throws Exception {
        int count = 1;
        Pageable pageable = defaultPageable();
        Page<PostDto> pageWithOneElement  = postPage(count, pageable);

        when(postService.getPosts(pageable))
                .thenReturn(pageWithOneElement);

        mockMvc
                .perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalElements").value(count))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpectAll(postDtoMatchers("$.content[0]", postDto));

        verify(postService)
                .getPosts(pageable);
    }

    @Test
    public void shouldReturnEmptyPageForNoPostWhenGettingPostsByUserSubscriptions() throws Exception {
        int count = 0;
        Pageable pageable = defaultPageable();
        Page<PostDto> emptyPage = postPage(count, pageable);

        when(postService.getPostsByUserSubscriptions(id, pageable))
                .thenReturn(emptyPage);

        mockMvc
                .perform(get(BASE_URL + "/subscriptions/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.totalElements").value(count))
                .andExpect(jsonPath("$.totalPages").value(1));

        verify(postService)
                .getPostsByUserSubscriptions(id, pageable);
    }

    @Test
    public void shouldReturnPageWithOnePostForOnePostWhenGettingPostsByUserSubscriptions() throws Exception {
        int count = 1;
        Pageable pageable = defaultPageable();
        Page<PostDto> pageWithOneElement  = postPage(count, pageable);

        when(postService.getPostsByUserSubscriptions(id, pageable))
                .thenReturn(pageWithOneElement);

        mockMvc
                .perform(get(BASE_URL + "/subscriptions/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalElements").value(count))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpectAll(postDtoMatchers("$.content[0]", postDto));

        verify(postService)
                .getPostsByUserSubscriptions(id, pageable);
    }

    @Test
    public void shouldReturnEmptyPageForNoPostWhenGettingPostsByUserId() throws Exception {
        int count = 0;
        Pageable pageable = defaultPageable();
        Page<PostDto> emptyPage = postPage(count, pageable);

        when(postService.getPostsByUserId(id, pageable))
                .thenReturn(emptyPage);

        mockMvc
                .perform(get(BASE_URL + "/users/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.totalElements").value(count))
                .andExpect(jsonPath("$.totalPages").value(1));

        verify(postService)
                .getPostsByUserId(id, pageable);
    }

    @Test
    public void shouldReturnPageWithOnePostForOnePostWhenGettingPostsByUserId() throws Exception {
        int count = 1;
        Pageable pageable = defaultPageable();
        Page<PostDto> pageWithOneElement  = postPage(count, pageable);

        when(postService.getPostsByUserId(id, pageable))
                .thenReturn(pageWithOneElement);

        mockMvc
                .perform(get(BASE_URL + "/users/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalElements").value(count))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpectAll(postDtoMatchers("$.content[0]", postDto));

        verify(postService)
                .getPostsByUserId(id, pageable);
    }

    @Test
    public void shouldReturnEmptyPageForNoPostWhenGettingPostsBySubredditId() throws Exception {
        int count = 0;
        Pageable pageable = defaultPageable();
        Page<PostDto> emptyPage = postPage(count, pageable);

        when(postService.getPostsBySubredditId(id, pageable))
                .thenReturn(emptyPage);

        mockMvc
                .perform(get(BASE_URL + "/subreddits/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.totalElements").value(count))
                .andExpect(jsonPath("$.totalPages").value(1));

        verify(postService)
                .getPostsBySubredditId(id, pageable);
    }

    @Test
    public void shouldReturnPageWithOnePostForOnePostWhenGettingPostsBySubredditId() throws Exception {
        int count = 1;
        Pageable pageable = defaultPageable();
        Page<PostDto> pageWithOneElement  = postPage(count, pageable);

        when(postService.getPostsBySubredditId(id, pageable))
                .thenReturn(pageWithOneElement);

        mockMvc
                .perform(get(BASE_URL + "/subreddits/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalElements").value(count))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpectAll(postDtoMatchers("$.content[0]", postDto));

        verify(postService)
                .getPostsBySubredditId(id, pageable);
    }

    @Test
    public void shouldReturnBadRequestForBlankTextWhenCreatingPost() throws Exception {
        PostDto blankTextPostDto = new PostDto(id, id, null, blankString, Post.INITIAL_SCORE, id, "title");

        String expectedMessage = messageSource.getMessage(
                "text.required",
                null,
                LocaleContextHolder.getLocale());

        mockMvc
                .perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(blankTextPostDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.text").value(expectedMessage));
    }

    @Test
    public void shouldReturnBadRequestForNotValidTextWhenCreatingPost() throws Exception {
        String text = getStringWithFixedLength(ValidationConstants.TEXT_MAX + 1);

        PostDto overMaxTextSizePostDto = new PostDto(id, id, null, text, Post.INITIAL_SCORE, id, "title");

        Object[] args = getArgs(ValidationConstants.TEXT_MIN, ValidationConstants.TEXT_MAX);
        String expectedMessage = messageSource.getMessage(
                "text.size.test",
                args,
                LocaleContextHolder.getLocale());

        mockMvc
                .perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(overMaxTextSizePostDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.text").value(expectedMessage));
    }

    @Test
    public void shouldReturnBadRequestForBlankTitleWhenCreatingPost() throws Exception {
        PostDto blankTitlePostDto = new PostDto(id, id, null, "text", Post.INITIAL_SCORE, id, blankString);

        String expectedMessage = messageSource.getMessage(
                "title.required",
                null,
                LocaleContextHolder.getLocale());

        mockMvc
                .perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(blankTitlePostDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value(expectedMessage));
    }

    @Test
    public void shouldReturnBadRequestForNotValidTitleWhenCreatingPost() throws Exception {
        String title = getStringWithFixedLength(ValidationConstants.TITLE_MAX + 1);

        PostDto overMaxTitleSizePostDto = new PostDto(id, id, null, "text", Post.INITIAL_SCORE, id, title);

        Object[] args = getArgs(ValidationConstants.TITLE_MIN, ValidationConstants.TITLE_MAX);
        String expectedMessage = messageSource.getMessage(
                "title.size.test",
                args,
                LocaleContextHolder.getLocale());

        mockMvc
                .perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(overMaxTitleSizePostDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value(expectedMessage));
    }

    @Test
    public void shouldReturnBadRequestForNullSubredditIdWhenCreatingPost() throws Exception {
        PostDto nullSubredditIdPostDto = new PostDto(id, id, null, "text", Post.INITIAL_SCORE, null, "title");

        String expectedMessage = messageSource.getMessage(
                "subredditId.required",
                null,
                LocaleContextHolder.getLocale());

        mockMvc
                .perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nullSubredditIdPostDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.subredditId").value(expectedMessage));
    }

    @Test
    public void shouldReturnPostResultWhenCreatingPost() throws Exception {
        when(postService.addPost(postDto))
                .thenReturn(postDto);

        mockMvc
                .perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isCreated())
                .andExpectAll(postDtoMatchers("$.", postDto));

        verify(postService)
                .addPost(postDto);
    }

    @Test
    public void shouldReturnConflictForNonMatchingUserIdAndCreatorIdWhenUpdatingPost() throws Exception {
        when(postService.updatePost(id, postDto))
                .thenThrow(new ContentUpdateNotAllowedException(ErrorMessages.CONTENT_UPDATE_NOT_ALLOWED));

        mockMvc
                .perform(put(BASE_URL + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(ErrorMessages.CONTENT_UPDATE_NOT_ALLOWED));

        verify(postService)
                .updatePost(id, postDto);
    }

    @Test
    public void shouldReturnUpdatedTitleAndTextPostWhenUpdatingPost() throws Exception {
        when(postService.updatePost(id, postDto))
                .thenReturn(postDto);

        mockMvc
                .perform(put(BASE_URL + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andExpectAll(postDtoMatchers("$.", postDto));

        verify(postService)
                .updatePost(id, postDto);
    }

    @Test
    public void shouldReturnForbiddenForMissingModeratorPrivilegesWhenDeletingPost() throws Exception {
        doThrow(new MissingModeratorPrivilegesException(ErrorMessages.MISSING_MODERATOR_PRIVILEGES))
                .when(postService)
                        .deletePost(id, id);

        mockMvc
                .perform(delete(BASE_URL + "/" + id + "?userId=" + id))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(ErrorMessages.MISSING_MODERATOR_PRIVILEGES));

        verify(postService)
                .deletePost(id, id);
    }

    @Test
    public void shouldReturnNoContentWhenDeletingPost() throws Exception {
        doNothing()
                .when(postService)
                    .deletePost(id, id);

        mockMvc
                .perform(delete(BASE_URL + "/" + id + "?userId=" + id))
                .andExpect(status().isNoContent());

        verify(postService)
                .deletePost(id, id);
    }

    private ResultMatcher[] postDtoMatchers(String prefix, PostDto dto) {
        return new ResultMatcher[] {
                jsonPath(prefix + "id").value(dto.id()),
                jsonPath(prefix + "userId").value(dto.userId()),
                jsonPath(prefix + "created").value(dto.created()),
                jsonPath(prefix + "text").value(dto.text()),
                jsonPath(prefix + "score").value(dto.score()),
                jsonPath(prefix + "subredditId").value(dto.subredditId()),
                jsonPath(prefix + "title").value(dto.title())
        };
    }

    private Pageable defaultPageable() {
        return PageRequest.of(
                0,
                PaginationConstants.POST_DEFAULT_SIZE,
                Sort.by(PaginationConstants.POST_DEFAULT_SORT).descending()
        );
    }

    private Page<PostDto> postPage(int size, Pageable pageable) {
        if (size == 0) {
            return Page.empty();
        }

        List<PostDto> posts = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            posts.add(postDto);
        }

        return new PageImpl<>(posts, pageable, posts.size());
    }

    private Object[] getArgs(int firstArg, int secondArg) {
        return new Object[] {
                String.valueOf(firstArg),
                String.valueOf(secondArg)
        };
    }

    private String getStringWithFixedLength(int length) {
        return "a".repeat(length);
    }
}
