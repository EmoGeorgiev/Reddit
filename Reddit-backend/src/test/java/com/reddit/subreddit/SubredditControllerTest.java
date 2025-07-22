package com.reddit.subreddit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reddit.exception.subreddit.MissingModeratorPrivilegesException;
import com.reddit.exception.subreddit.SubredditAlreadyExistsException;
import com.reddit.exception.subreddit.SubredditNotFoundException;
import com.reddit.subreddit.dto.SubredditDto;
import com.reddit.subreddit.dto.SubredditUpdateTitleDto;
import com.reddit.util.ErrorMessages;
import com.reddit.util.PaginationConstants;
import com.reddit.util.ValidationConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;

import static com.reddit.util.TestUtils.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SubredditController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SubredditControllerTest {
    private static final String BASE_URL = "/api/subreddits";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MessageSource messageSource;
    @MockitoBean
    private SubredditService subredditService;
    private final Long id = 1L;
    private final String title = "title";
    private final Integer subredditSize = 0;
    private final SubredditDto subredditDto = new SubredditDto(id, title, subredditSize);
    private final SubredditUpdateTitleDto subredditUpdateTitleDto = new SubredditUpdateTitleDto(title, id);

    @Test
    public void shouldReturnNotFoundForInvalidIdWhenGettingSubredditById() throws Exception {
        Long invalidId = -1L;
        when(subredditService.getSubredditById(invalidId))
                .thenThrow(new SubredditNotFoundException(ErrorMessages.SUBREDDIT_NOT_FOUND));

        mockMvc
                .perform(get(BASE_URL + "/id/" + invalidId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(ErrorMessages.SUBREDDIT_NOT_FOUND));

        verify(subredditService)
                .getSubredditById(invalidId);
    }

    @Test
    public void shouldReturnSubredditForValidIdWhenGettingSubredditById() throws Exception {
        when(subredditService.getSubredditById(id))
                .thenReturn(subredditDto);

        mockMvc
                .perform(get(BASE_URL + "/id/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(subredditDtoMatchers("$.", subredditDto));

        verify(subredditService)
                .getSubredditById(id);
    }

    @Test
    public void shouldReturnNotFoundForInvalidTitleWhenGettingSubredditByTitle() throws Exception {
        String title = "title";
        when(subredditService.getSubredditByTitle(title))
                .thenThrow(new SubredditNotFoundException(ErrorMessages.SUBREDDIT_NOT_FOUND));

        mockMvc
                .perform(get(BASE_URL + "/title/" + title))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(ErrorMessages.SUBREDDIT_NOT_FOUND));

        verify(subredditService)
                .getSubredditByTitle(title);
    }

    @Test
    public void shouldReturnSubredditValidTitleWhenGettingSubredditByTitle() throws Exception {
        String title = "title";
        when(subredditService.getSubredditByTitle(title))
                .thenReturn(subredditDto);

        mockMvc
                .perform(get(BASE_URL + "/title/" + title))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(subredditDtoMatchers("$.", subredditDto));

        verify(subredditService)
                .getSubredditByTitle(title);
    }

    @Test
    public void shouldReturnEmptyPageForNoSubredditTitleContainingWordWhenGettingSubredditsWhereTitleContainsWord() throws Exception {
        int expectedTotalPages = 1;
        int count = 0;
        Pageable pageable = getPageable(PaginationConstants.SUBREDDIT_BY_TITLE_CONTAINS_SIZE, PaginationConstants.SUBREDDIT_BY_TITLE_CONTAINS_SORT);
        Page<SubredditDto> emptyPage = getPage(subredditDto, count, pageable);

        String word = "word";

        when(subredditService.getSubredditsWhereTitleContainsWord(word, pageable))
                .thenReturn(emptyPage);

        mockMvc
                .perform(get(BASE_URL + "/search?word=" + word))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.totalElements").value(count))
                .andExpect(jsonPath("$.totalPages").value(expectedTotalPages));

        verify(subredditService)
                .getSubredditsWhereTitleContainsWord(word, pageable);
    }

    @Test
    public void shouldReturnPageWithOneSubredditForOneSubredditTitleContainingWordWhenGettingSubredditsWhereTitleContainsWord() throws Exception {
        int expectedTotalPages = 1;
        int count = 1;
        Pageable pageable = getPageable(PaginationConstants.SUBREDDIT_BY_TITLE_CONTAINS_SIZE, PaginationConstants.SUBREDDIT_BY_TITLE_CONTAINS_SORT);
        Page<SubredditDto> pageWithOneElement = getPage(subredditDto, count, pageable);

        String word = "word";

        when(subredditService.getSubredditsWhereTitleContainsWord(word, pageable))
                .thenReturn(pageWithOneElement);

        mockMvc
                .perform(get(BASE_URL + "/search?word=" + word))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalElements").value(count))
                .andExpect(jsonPath("$.totalPages").value(expectedTotalPages))
                .andExpectAll(subredditDtoMatchers("$.content[0].", subredditDto));

        verify(subredditService)
                .getSubredditsWhereTitleContainsWord(word, pageable);
    }

    @Test
    public void shouldReturnEmptySetForUserWithNoSubredditSubscriptionsWhenGettingSubredditsByUserId() throws Exception {
        when(subredditService.getSubredditsByUserId(id))
                .thenReturn(List.of());

        mockMvc
                .perform(get(BASE_URL + "/users/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());

        verify(subredditService)
                .getSubredditsByUserId(id);
    }

    @Test
    public void shouldReturnSetWithOneElementForUserWithOneSubredditSubscriptionsWhenGettingSubredditsByUserId() throws Exception {
        when(subredditService.getSubredditsByUserId(id))
                .thenReturn(List.of(subredditDto));

        mockMvc
                .perform(get(BASE_URL + "/users/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(subredditDtoMatchers("$.[0].", subredditDto));

        verify(subredditService)
                .getSubredditsByUserId(id);
    }

    @Test
    public void shouldReturnEmptySetForModeratorWithNoModeratedSubredditWhenGettingSubredditsByModeratorId() throws Exception {
        when(subredditService.getSubredditsByModeratorId(id))
                .thenReturn(List.of());

        mockMvc
                .perform(get(BASE_URL + "/moderators/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());

        verify(subredditService)
                .getSubredditsByModeratorId(id);
    }

    @Test
    public void shouldReturnSetWithOneElementForModeratorWithOneModeratedSubredditWhenGettingSubredditsByModeratorId() throws Exception {
        when(subredditService.getSubredditsByModeratorId(id))
                .thenReturn(List.of(subredditDto));

        mockMvc
                .perform(get(BASE_URL + "/moderators/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(subredditDtoMatchers("$.[0].", subredditDto));

        verify(subredditService)
                .getSubredditsByModeratorId(id);
    }

    @Test
    public void shouldReturnBadRequestForBlankTitleWhenCreatingSubreddit() throws Exception {
        SubredditDto blankTitleSubredditDto = new SubredditDto(id, BLANK_STRING, subredditSize);

        String expectedMessage = messageSource.getMessage(
                "title.required",
                null,
                LocaleContextHolder.getLocale());

        mockMvc
                .perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(blankTitleSubredditDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value(expectedMessage));
    }

    @Test
    public void shouldReturnBadRequestForNonValidTitleWhenCreatingSubreddit() throws Exception {
        String title = getStringWithFixedLength(ValidationConstants.TITLE_MAX + 1);

        SubredditDto overMaxTitleSizeSubredditDto = new SubredditDto(id, title, subredditSize);

        Object[] args = getArgs(ValidationConstants.TITLE_MIN, ValidationConstants.TITLE_MAX);
        String expectedMessage = messageSource.getMessage(
                "title.size.test",
                args,
                LocaleContextHolder.getLocale());

        mockMvc
                .perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(overMaxTitleSizeSubredditDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value(expectedMessage));
    }

    @Test
    public void shouldReturnConflictForAlreadyExistingTitleWhenCreatingSubreddit() throws Exception {
        when(subredditService.addSubreddit(subredditDto, id))
                .thenThrow(new SubredditAlreadyExistsException(ErrorMessages.SUBREDDIT_ALREADY_EXISTS));

        mockMvc
                .perform(post(BASE_URL + "?creatorId=" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subredditDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(ErrorMessages.SUBREDDIT_ALREADY_EXISTS));

        verify(subredditService)
                .addSubreddit(subredditDto, id);
    }

    @Test
    public void shouldReturnSubredditResultForValidSubredditTitleAndUserIdWhenCreatingSubreddit() throws Exception {
        when(subredditService.addSubreddit(subredditDto, id))
                .thenReturn(subredditDto);

        mockMvc
                .perform(post(BASE_URL + "?creatorId=" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subredditDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(subredditDtoMatchers("$.", subredditDto));

        verify(subredditService)
                .addSubreddit(subredditDto, id);
    }

    @Test
    public void shouldReturnBadRequestForBlankTitleWhenUpdatingSubredditTitle() throws Exception {
        SubredditUpdateTitleDto blankTitleSubredditUpdateTitleDto = new SubredditUpdateTitleDto(BLANK_STRING, id);

        String expectedMessage = messageSource.getMessage(
                "title.required",
                null,
                LocaleContextHolder.getLocale());

        mockMvc
                .perform(put(BASE_URL + "/" + id + "/title")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(blankTitleSubredditUpdateTitleDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value(expectedMessage));
    }

    @Test
    public void shouldReturnBadRequestForNonValidTitleWhenUpdatingSubredditTitle() throws Exception {
        String title = getStringWithFixedLength(ValidationConstants.TITLE_MAX + 1);

        SubredditUpdateTitleDto overMaxTitleSizeSubredditUpdateTitleDto = new SubredditUpdateTitleDto(title, id);

        Object[] args = getArgs(ValidationConstants.TITLE_MIN, ValidationConstants.TITLE_MAX);
        String expectedMessage = messageSource.getMessage(
                "title.size.test",
                args,
                LocaleContextHolder.getLocale());

        mockMvc
                .perform(put(BASE_URL + "/" + id + "/title")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(overMaxTitleSizeSubredditUpdateTitleDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value(expectedMessage));
    }

    @Test
    public void shouldReturnForbiddenForUserNotBeingAModeratorWhenUpdatingSubredditTitle() throws Exception {
        SubredditUpdateTitleDto subredditUpdateTitleDto1 = new SubredditUpdateTitleDto(title, id);

        when(subredditService.updateSubredditTitle(id, subredditUpdateTitleDto1))
                .thenThrow(new MissingModeratorPrivilegesException(ErrorMessages.MISSING_MODERATOR_PRIVILEGES));

        mockMvc
                .perform(put(BASE_URL + "/" + id + "/title")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subredditUpdateTitleDto)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(ErrorMessages.MISSING_MODERATOR_PRIVILEGES));

        verify(subredditService)
                .updateSubredditTitle(id, subredditUpdateTitleDto1);
    }

    @Test
    public void shouldReturnUpdateSubredditForValidModeratorPrivilegesWhenUpdatingSubredditTitle() throws Exception {
        SubredditUpdateTitleDto subredditUpdateTitleDto1 = new SubredditUpdateTitleDto(title, id);

        when(subredditService.updateSubredditTitle(id, subredditUpdateTitleDto1))
                .thenReturn(subredditDto);

        mockMvc
                .perform(put(BASE_URL + "/" + id + "/title")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subredditUpdateTitleDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(subredditDtoMatchers("$.", subredditDto));

        verify(subredditService)
                .updateSubredditTitle(id, subredditUpdateTitleDto1);
    }

    @Test
    public void shouldReturnForbiddenForMissingModeratorPrivilegesWhenDeletingSubreddit() throws Exception {
        doThrow(new MissingModeratorPrivilegesException(ErrorMessages.MISSING_MODERATOR_PRIVILEGES))
                .when(subredditService)
                        .deleteSubreddit(id, id);

        mockMvc
                .perform(delete(BASE_URL + "/" + id + "?moderatorId=" + id))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(ErrorMessages.MISSING_MODERATOR_PRIVILEGES));

        verify(subredditService)
                .deleteSubreddit(id, id);
    }

    @Test
    public void shouldReturnNoContentForValidModeratorWhenDeletingSubreddit() throws Exception {
        doNothing()
                .when(subredditService)
                    .deleteSubreddit(id, id);

        mockMvc
                .perform(delete(BASE_URL + "/" + id + "?moderatorId=" + id))
                .andExpect(status().isNoContent());


        verify(subredditService)
                .deleteSubreddit(id, id);
    }

    private ResultMatcher[] subredditDtoMatchers(String prefix, SubredditDto dto) {
        return new ResultMatcher[] {
                jsonPath(prefix + "id").value(dto.id()),
                jsonPath(prefix + "title").value(dto.title()),
                jsonPath(prefix + "userCount").value(dto.userCount())
        };
    }
}
