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
                .andExpect(jsonPath("id").value(postDto.id()))
                .andExpect(jsonPath("userId").value(postDto.userId()))
                .andExpect(jsonPath("created").value(postDto.created()))
                .andExpect(jsonPath("text").value(postDto.text()))
                .andExpect(jsonPath("score").value(postDto.score()))
                .andExpect(jsonPath("subredditId").value(postDto.subredditId()))
                .andExpect(jsonPath("title").value(postDto.title()));

        verify(postService)
                .getPost(id);
    }

    @Test
    public void shouldReturnEmptyPageForNoPostWhenGettingPosts() throws Exception {
        Pageable pageable = PageRequest.of(0,
                PaginationConstants.POST_DEFAULT_SIZE,
                Sort.by(PaginationConstants.POST_DEFAULT_SORT).descending());
        Page<PostDto> emptyPage = Page.empty();

        when(postService.getPosts(pageable))
                .thenReturn(emptyPage);

        mockMvc
                .perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.totalElements").value(0))
                .andExpect(jsonPath("$.totalPages").value(1));


        verify(postService)
                .getPosts(pageable);
    }

    @Test
    public void shouldReturnPageWithOnePostForOnePostWhenGettingPosts() throws Exception {
        List<PostDto> posts = List.of(postDto);
        Pageable pageable = PageRequest.of(
                0,
                PaginationConstants.POST_DEFAULT_SIZE,
                Sort.by(PaginationConstants.POST_DEFAULT_SORT).descending());
        Page<PostDto> pageWithOneElement  = new PageImpl<>(posts, pageable, posts.size());

        when(postService.getPosts(pageable))
                .thenReturn(pageWithOneElement);

        mockMvc
                .perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.content[0].id").value(postDto.id()))
                .andExpect(jsonPath("$.content[0].userId").value(postDto.userId()))
                .andExpect(jsonPath("$.content[0].created").value(postDto.created()))
                .andExpect(jsonPath("$.content[0].text").value(postDto.text()))
                .andExpect(jsonPath("$.content[0].score").value(postDto.score()))
                .andExpect(jsonPath("$.content[0].subredditId").value(postDto.subredditId()))
                .andExpect(jsonPath("$.content[0].title").value(postDto.title()));

        verify(postService)
                .getPosts(pageable);
    }

    @Test
    public void shouldReturnEmptyPageForNoPostWhenGettingPostsByUserSubscriptions() throws Exception {
        Pageable pageable = PageRequest.of(0,
                PaginationConstants.POST_DEFAULT_SIZE,
                Sort.by(PaginationConstants.POST_DEFAULT_SORT).descending());
        Page<PostDto> emptyPage = Page.empty();

        when(postService.getPostsByUserSubscriptions(id, pageable))
                .thenReturn(emptyPage);

        mockMvc
                .perform(get(BASE_URL + "/subscriptions/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.totalElements").value(0))
                .andExpect(jsonPath("$.totalPages").value(1));


        verify(postService)
                .getPostsByUserSubscriptions(id, pageable);
    }

    @Test
    public void shouldReturnPageWithOnePostForOnePostWhenGettingPostsByUserSubscriptions() throws Exception {
        List<PostDto> posts = List.of(postDto);
        Pageable pageable = PageRequest.of(
                0,
                PaginationConstants.POST_DEFAULT_SIZE,
                Sort.by(PaginationConstants.POST_DEFAULT_SORT).descending());
        Page<PostDto> pageWithOneElement  = new PageImpl<>(posts, pageable, posts.size());

        when(postService.getPostsByUserSubscriptions(id, pageable))
                .thenReturn(pageWithOneElement);

        mockMvc
                .perform(get(BASE_URL + "/subscriptions/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.content[0].id").value(postDto.id()))
                .andExpect(jsonPath("$.content[0].userId").value(postDto.userId()))
                .andExpect(jsonPath("$.content[0].created").value(postDto.created()))
                .andExpect(jsonPath("$.content[0].text").value(postDto.text()))
                .andExpect(jsonPath("$.content[0].score").value(postDto.score()))
                .andExpect(jsonPath("$.content[0].subredditId").value(postDto.subredditId()))
                .andExpect(jsonPath("$.content[0].title").value(postDto.title()));

        verify(postService)
                .getPostsByUserSubscriptions(id, pageable);
    }

    @Test
    public void shouldReturnEmptyPageForNoPostWhenGettingPostsByUserId() throws Exception {
        Pageable pageable = PageRequest.of(0,
                PaginationConstants.POST_DEFAULT_SIZE,
                Sort.by(PaginationConstants.POST_DEFAULT_SORT).descending());
        Page<PostDto> emptyPage = Page.empty();

        when(postService.getPostsByUserId(id, pageable))
                .thenReturn(emptyPage);

        mockMvc
                .perform(get(BASE_URL + "/users/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.totalElements").value(0))
                .andExpect(jsonPath("$.totalPages").value(1));


        verify(postService)
                .getPostsByUserId(id, pageable);
    }

    @Test
    public void shouldReturnPageWithOnePostForOnePostWhenGettingPostsByUserId() throws Exception {
        List<PostDto> posts = List.of(postDto);
        Pageable pageable = PageRequest.of(
                0,
                PaginationConstants.POST_DEFAULT_SIZE,
                Sort.by(PaginationConstants.POST_DEFAULT_SORT).descending());
        Page<PostDto> pageWithOneElement  = new PageImpl<>(posts, pageable, posts.size());

        when(postService.getPostsByUserId(id, pageable))
                .thenReturn(pageWithOneElement);

        mockMvc
                .perform(get(BASE_URL + "/users/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.content[0].id").value(postDto.id()))
                .andExpect(jsonPath("$.content[0].userId").value(postDto.userId()))
                .andExpect(jsonPath("$.content[0].created").value(postDto.created()))
                .andExpect(jsonPath("$.content[0].text").value(postDto.text()))
                .andExpect(jsonPath("$.content[0].score").value(postDto.score()))
                .andExpect(jsonPath("$.content[0].subredditId").value(postDto.subredditId()))
                .andExpect(jsonPath("$.content[0].title").value(postDto.title()));

        verify(postService)
                .getPostsByUserId(id, pageable);
    }

    @Test
    public void shouldReturnEmptyPageForNoPostWhenGettingPostsBySubredditId() throws Exception {
        Pageable pageable = PageRequest.of(0,
                PaginationConstants.POST_DEFAULT_SIZE,
                Sort.by(PaginationConstants.POST_DEFAULT_SORT).descending());
        Page<PostDto> emptyPage = Page.empty();

        when(postService.getPostsBySubredditId(id, pageable))
                .thenReturn(emptyPage);

        mockMvc
                .perform(get(BASE_URL + "/subreddits/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.totalElements").value(0))
                .andExpect(jsonPath("$.totalPages").value(1));


        verify(postService)
                .getPostsBySubredditId(id, pageable);
    }

    @Test
    public void shouldReturnPageWithOnePostForOnePostWhenGettingPostsBySubredditId() throws Exception {
        List<PostDto> posts = List.of(postDto);
        Pageable pageable = PageRequest.of(
                0,
                PaginationConstants.POST_DEFAULT_SIZE,
                Sort.by(PaginationConstants.POST_DEFAULT_SORT).descending());
        Page<PostDto> pageWithOneElement  = new PageImpl<>(posts, pageable, posts.size());

        when(postService.getPostsBySubredditId(id, pageable))
                .thenReturn(pageWithOneElement);

        mockMvc
                .perform(get(BASE_URL + "/subreddits/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.content[0].id").value(postDto.id()))
                .andExpect(jsonPath("$.content[0].userId").value(postDto.userId()))
                .andExpect(jsonPath("$.content[0].created").value(postDto.created()))
                .andExpect(jsonPath("$.content[0].text").value(postDto.text()))
                .andExpect(jsonPath("$.content[0].score").value(postDto.score()))
                .andExpect(jsonPath("$.content[0].subredditId").value(postDto.subredditId()))
                .andExpect(jsonPath("$.content[0].title").value(postDto.title()));

        verify(postService)
                .getPostsBySubredditId(id, pageable);
    }

    @Test
    public void shouldReturnBadRequestForBlankTextWhenCreatingPost() throws Exception {
        String blankText = "          ";
        PostDto blankTextPostDto = new PostDto(id, id, null, blankText, Post.INITIAL_SCORE, id, "title");

        String expectedMessage = messageSource.getMessage("text.required", null, LocaleContextHolder.getLocale());

        mockMvc
                .perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(blankTextPostDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.text").value(expectedMessage));
    }

    @Test
    public void shouldReturnBadRequestForNotValidTextWhenCreatingPost() throws Exception {
        int length = ValidationConstants.TEXT_MAX + 1;
        String text = "a".repeat(length);

        PostDto overMaxTextSizePostDto = new PostDto(id, id, null, text, Post.INITIAL_SCORE, id, "title");

        Object[] args = new Object[] { String.valueOf(ValidationConstants.TEXT_MIN), String.valueOf(ValidationConstants.TEXT_MAX) };
        String expectedMessage = messageSource.getMessage("text.size.test", args, LocaleContextHolder.getLocale());

        mockMvc
                .perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(overMaxTextSizePostDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.text").value(expectedMessage));
    }

    @Test
    public void shouldReturnBadRequestForBlankTitleWhenCreatingPost() throws Exception {
        String blankTitle = "          ";
        PostDto blankTitlePostDto = new PostDto(id, id, null, "text", Post.INITIAL_SCORE, id, blankTitle);

        String expectedMessage = messageSource.getMessage("title.required", null, LocaleContextHolder.getLocale());

        mockMvc
                .perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(blankTitlePostDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value(expectedMessage));
    }

    @Test
    public void shouldReturnBadRequestForNotValidTitleWhenCreatingPost() throws Exception {
        int length = ValidationConstants.TITLE_MAX + 1;
        String title = "a".repeat(length);

        PostDto overMaxTitleSizePostDto = new PostDto(id, id, null, "text", Post.INITIAL_SCORE, id, title);

        Object[] args = new Object[] { String.valueOf(ValidationConstants.TITLE_MIN), String.valueOf(ValidationConstants.TITLE_MAX) };
        String expectedMessage = messageSource.getMessage("title.size.test", args, LocaleContextHolder.getLocale());

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

        String expectedMessage = messageSource.getMessage("subredditId.required", null, LocaleContextHolder.getLocale());

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
                .andExpect(jsonPath("$.id").value(postDto.id()))
                .andExpect(jsonPath("$.userId").value(postDto.userId()))
                .andExpect(jsonPath("$.created").value(postDto.created()))
                .andExpect(jsonPath("$.text").value(postDto.text()))
                .andExpect(jsonPath("$.score").value(postDto.score()))
                .andExpect(jsonPath("$.subredditId").value(postDto.subredditId()))
                .andExpect(jsonPath("$.title").value(postDto.title()));

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
                .andExpect(jsonPath("$.id").value(postDto.id()))
                .andExpect(jsonPath("$.userId").value(postDto.userId()))
                .andExpect(jsonPath("$.created").value(postDto.created()))
                .andExpect(jsonPath("$.text").value(postDto.text()))
                .andExpect(jsonPath("$.score").value(postDto.score()))
                .andExpect(jsonPath("$.subredditId").value(postDto.subredditId()))
                .andExpect(jsonPath("$.title").value(postDto.title()));

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
}
