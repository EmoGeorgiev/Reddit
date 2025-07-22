package com.reddit.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reddit.exception.subreddit.MissingModeratorPrivilegesException;
import com.reddit.exception.user.*;
import com.reddit.user.dto.ModeratorUpdateDto;
import com.reddit.user.dto.UpdatePasswordDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static com.reddit.util.TestUtils.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
    private static final String BASE_URL = "/api/users";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MessageSource messageSource;
    @MockitoBean
    private UserService userService;
    private final Long id = 1L;
    private final String subredditTitle = "subredditTitle";
    private final String username = "username";
    private final UserDto userDto = new UserDto(id, username);

    @Test
    public void shouldReturnNotFoundForInvalidIdWhenGettingUserById() throws Exception {
        Long invalidId = -1L;

        when(userService.getUser(invalidId))
                .thenThrow(new UserNotFoundException(ErrorMessages.USER_NOT_FOUND));

        mockMvc
                .perform(get(BASE_URL + "/id/" + invalidId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(ErrorMessages.USER_NOT_FOUND));

        verify(userService)
                .getUser(invalidId);
    }

    @Test
    public void shouldReturnUserForValidIdWhenGettingUserById() throws Exception {
        when(userService.getUser(id))
                .thenReturn(userDto);

        mockMvc
                .perform(get(BASE_URL + "/id/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(userResultMatchers("$.", userDto));

        verify(userService)
                .getUser(id);
    }

    @Test
    public void shouldReturnEmptyPageForNoUsersWhenGettingUsers() throws Exception {
        int expectedTotalPages = 1;
        int count = 0;
        Pageable pageable = getPageable(PaginationConstants.USER_DEFAULT_SIZE, PaginationConstants.USER_DEFAULT_SORT);
        Page<UserDto> emptyPage = getPage(userDto, count, pageable);

        when(userService.getUsers(pageable))
                .thenReturn(emptyPage);

        mockMvc
                .perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.totalElements").value(count))
                .andExpect(jsonPath("$.totalPages").value(expectedTotalPages));

        verify(userService)
                .getUsers(pageable);
    }

    @Test
    public void shouldReturnPageWithOneUserForOneUserWhenGettingUsers() throws Exception {
        int expectedTotalPages = 1;
        int count = 1;
        Pageable pageable = getPageable(PaginationConstants.USER_DEFAULT_SIZE, PaginationConstants.USER_DEFAULT_SORT);
        Page<UserDto> pageWithOneElement = getPage(userDto, count, pageable);

        when(userService.getUsers(pageable))
                .thenReturn(pageWithOneElement);

        mockMvc
                .perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalElements").value(count))
                .andExpect(jsonPath("$.totalPages").value(expectedTotalPages))
                .andExpectAll(userResultMatchers("$.content[0].", userDto));

        verify(userService)
                .getUsers(pageable);
    }

    @Test
    public void shouldReturnPageWithTwoUsersForTwoUsersWhenGettingUsers() throws Exception {
        int expectedTotalPages = 1;
        int count = 2;
        Pageable pageable = getPageable(PaginationConstants.USER_DEFAULT_SIZE, PaginationConstants.USER_DEFAULT_SORT);
        Page<UserDto> pageWithTwoElements = getPage(userDto, count, pageable);

        when(userService.getUsers(pageable))
                .thenReturn(pageWithTwoElements);

        mockMvc
                .perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalElements").value(count))
                .andExpect(jsonPath("$.totalPages").value(expectedTotalPages))
                .andExpectAll(userResultMatchers("$.content[0].", userDto))
                .andExpectAll(userResultMatchers("$.content[1].", userDto));

        verify(userService)
                .getUsers(pageable);
    }

    @Test
    public void shouldReturnEmptyPageForNoUsernameContainingWordWhenGettingUsersWhereUsernameContainsWord() throws Exception {
        int expectedTotalPages = 1;
        int count = 0;
        Pageable pageable = getPageable(PaginationConstants.USER_DEFAULT_SIZE, PaginationConstants.USER_DEFAULT_SORT);
        Page<UserDto> emptyPage = getPage(userDto, count, pageable);

        String word = "word";

        when(userService.getUsersWhereUsernameContainsWord(word, pageable))
                .thenReturn(emptyPage);

        mockMvc
                .perform(get(BASE_URL + "/search?word=" + word))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.totalElements").value(count))
                .andExpect(jsonPath("$.totalPages").value(expectedTotalPages));

        verify(userService)
                .getUsersWhereUsernameContainsWord(word, pageable);
    }

    @Test
    public void shouldReturnPageWithOneUserForOneUsernameContainingWordWhenGettingUsersWhereUsernameContainsWord() throws Exception {
        int expectedTotalPages = 1;
        int count = 1;
        Pageable pageable = getPageable(PaginationConstants.USER_DEFAULT_SIZE, PaginationConstants.USER_DEFAULT_SORT);
        Page<UserDto> pageWithOneElement = getPage(userDto, count, pageable);

        String word = "word";

        when(userService.getUsersWhereUsernameContainsWord(word, pageable))
                .thenReturn(pageWithOneElement);

        mockMvc
                .perform(get(BASE_URL + "/search?word=" + word))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalElements").value(count))
                .andExpect(jsonPath("$.totalPages").value(expectedTotalPages))
                .andExpectAll(userResultMatchers("$.content[0].", userDto));

        verify(userService)
                .getUsersWhereUsernameContainsWord(word, pageable);
    }

    @Test
    public void shouldReturnPageWithTwoUsersForTwoUsernamesContainingWordWhenGettingUsersWhereUsernameContainsWord() throws Exception {
        int expectedTotalPages = 1;
        int count = 2;
        Pageable pageable = getPageable(PaginationConstants.USER_DEFAULT_SIZE, PaginationConstants.USER_DEFAULT_SORT);
        Page<UserDto> pageWithTwoElements = getPage(userDto, count, pageable);

        String word = "word";

        when(userService.getUsersWhereUsernameContainsWord(word, pageable))
                .thenReturn(pageWithTwoElements);

        mockMvc
                .perform(get(BASE_URL + "/search?word=" + word))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalElements").value(count))
                .andExpect(jsonPath("$.totalPages").value(expectedTotalPages))
                .andExpectAll(userResultMatchers("$.content[0].", userDto))
                .andExpectAll(userResultMatchers("$.content[1].", userDto));

        verify(userService)
                .getUsersWhereUsernameContainsWord(word, pageable);
    }

    @Test
    public void shouldReturnBadRequestForBlankUsernameWhenUpdatingUsername() throws Exception {
        UserDto blankUsernameUserDto = new UserDto(id, BLANK_STRING);

        String expectedMessage = messageSource.getMessage(
                "username.required",
                null,
                LocaleContextHolder.getLocale());

        mockMvc
                .perform(put(BASE_URL + "/" + id + "/username")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(blankUsernameUserDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.username").value(expectedMessage));
    }

    @Test
    public void shouldReturnBadRequestForOverMaxSizeUsernameWhenUpdatingUsername() throws Exception {
        String overMaxSizeUsername = getStringWithFixedLength(ValidationConstants.USERNAME_MAX + 1);
        UserDto overMaxSizeUsernameUserDto = new UserDto(id, overMaxSizeUsername);

        Object[] args = getArgs(ValidationConstants.USERNAME_MIN, ValidationConstants.USERNAME_MAX);
        String expectedMessage = messageSource.getMessage(
                "username.size.test",
                args,
                LocaleContextHolder.getLocale());

        mockMvc
                .perform(put(BASE_URL + "/" + id + "/username")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(overMaxSizeUsernameUserDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.username").value(expectedMessage));
    }

    @Test
    public void shouldReturnConflictForAlreadyExistingUsernameWhenUpdatingUsername() throws Exception {
        when(userService.updateUsername(id, userDto))
                .thenThrow(new UsernameAlreadyExistsException(ErrorMessages.USERNAME_ALREADY_EXISTS));

        mockMvc
                .perform(put(BASE_URL + "/" + id + "/username")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(ErrorMessages.USERNAME_ALREADY_EXISTS));

        verify(userService)
                .updateUsername(id, userDto);
    }

    @Test
    public void shouldReturnUpdatedUserForValidNewUsernameWhenUpdatingUsername() throws Exception {
        when(userService.updateUsername(id, userDto))
                .thenReturn(userDto);

        mockMvc
                .perform(put(BASE_URL + "/" + id + "/username")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(userResultMatchers("$.", userDto));

        verify(userService)
                .updateUsername(id, userDto);
    }

    @Test
    public void shouldReturnBadRequestForBlankOldPasswordWhenUpdatingPassword() throws Exception {
        UpdatePasswordDto blankOldPasswordUpdatePasswordDto = new UpdatePasswordDto(BLANK_STRING, "new password");

        String expectedMessage = messageSource.getMessage(
                "password.required",
                null,
                LocaleContextHolder.getLocale());

        mockMvc
                .perform(put(BASE_URL + "/" + id + "/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(blankOldPasswordUpdatePasswordDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.oldPassword").value(expectedMessage));
    }

    @Test
    public void shouldReturnBadRequestForBlankNewPasswordWhenUpdatingPassword() throws Exception {
        UpdatePasswordDto blankNewPasswordUpdatePasswordDto = new UpdatePasswordDto("old password", BLANK_STRING);

        String expectedMessage = messageSource.getMessage(
                "password.required",
                null,
                LocaleContextHolder.getLocale());

        mockMvc
                .perform(put(BASE_URL + "/" + id + "/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(blankNewPasswordUpdatePasswordDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.newPassword").value(expectedMessage));
    }

    @Test
    public void shouldReturnBadRequestForBlankOldPasswordAndBlankNewPasswordWhenUpdatingPassword() throws Exception {
        UpdatePasswordDto blankOldPasswordAndBlankNewPasswordUpdatePasswordDto = new UpdatePasswordDto(BLANK_STRING, BLANK_STRING);

        String expectedMessage = messageSource.getMessage(
                "password.required",
                null,
                LocaleContextHolder.getLocale());

        mockMvc
                .perform(put(BASE_URL + "/" + id + "/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(blankOldPasswordAndBlankNewPasswordUpdatePasswordDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.oldPassword").value(expectedMessage))
                .andExpect(jsonPath("$.newPassword").value(expectedMessage));
    }

    @Test
    public void shouldReturnBadRequestForOverMaxSizeOldPasswordWhenUpdatingPassword() throws Exception {
        String overMaxSizePassword = getStringWithFixedLength(ValidationConstants.PASSWORD_MAX + 1);
        UpdatePasswordDto overMaxSizeOldPasswordUpdatePasswordDto = new UpdatePasswordDto(overMaxSizePassword, "new password");

        Object[] args = getArgs(ValidationConstants.PASSWORD_MIN, ValidationConstants.PASSWORD_MAX);
        String expectedMessage = messageSource.getMessage(
                "password.size.test",
                args,
                LocaleContextHolder.getLocale());

        mockMvc
                .perform(put(BASE_URL + "/" + id + "/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(overMaxSizeOldPasswordUpdatePasswordDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.oldPassword").value(expectedMessage));
    }

    @Test
    public void shouldReturnBadRequestForOverMaxSizeNewPasswordWhenUpdatingPassword() throws Exception {
        String overMaxSizePassword = getStringWithFixedLength(ValidationConstants.PASSWORD_MAX + 1);
        UpdatePasswordDto overMaxSizeNewPasswordUpdatePasswordDto = new UpdatePasswordDto("old password", overMaxSizePassword);

        Object[] args = getArgs(ValidationConstants.PASSWORD_MIN, ValidationConstants.PASSWORD_MAX);
        String expectedMessage = messageSource.getMessage(
                "password.size.test",
                args,
                LocaleContextHolder.getLocale());

        mockMvc
                .perform(put(BASE_URL + "/" + id + "/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(overMaxSizeNewPasswordUpdatePasswordDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.newPassword").value(expectedMessage));
    }

    @Test
    public void shouldReturnBadRequestForOverMaxSizeOldPasswordAndOverMaxSizeNewPasswordWhenUpdatingPassword() throws Exception {
        String overMaxSizePassword = getStringWithFixedLength(ValidationConstants.PASSWORD_MAX + 1);
        UpdatePasswordDto overMaxSizeOldPasswordAndOverMaxSizeNewPasswordUpdatePasswordDto = new UpdatePasswordDto(overMaxSizePassword, overMaxSizePassword);

        Object[] args = getArgs(ValidationConstants.PASSWORD_MIN, ValidationConstants.PASSWORD_MAX);
        String expectedMessage = messageSource.getMessage(
                "password.size.test",
                args,
                LocaleContextHolder.getLocale());

        mockMvc
                .perform(put(BASE_URL + "/" + id + "/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(overMaxSizeOldPasswordAndOverMaxSizeNewPasswordUpdatePasswordDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.oldPassword").value(expectedMessage))
                .andExpect(jsonPath("$.newPassword").value(expectedMessage));
    }

    @Test
    public void shouldReturnBadRequestForNonMatchingPasswordsWhenUpdatingPassword() throws Exception {
        UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto("password", "password");

        when(userService.updatePassword(id, updatePasswordDto))
                .thenThrow(new PasswordsDoNotMatchException(ErrorMessages.PASSWORDS_DO_NOT_MATCH));

        mockMvc
                .perform(put(BASE_URL + "/" + id + "/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePasswordDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ErrorMessages.PASSWORDS_DO_NOT_MATCH));

        verify(userService)
                .updatePassword(id, updatePasswordDto);
    }

    @Test
    public void shouldReturnConflictForNewPasswordBeingTheSameAsOldPasswordWhenUpdatingPassword() throws Exception {
        UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto("password", "password");

        when(userService.updatePassword(id, updatePasswordDto))
                .thenThrow(new NewPassWordCannotBeOldPasswordException(ErrorMessages.NEW_PASSWORD_CANNOT_BE_OLD_PASSWORD));

        mockMvc
                .perform(put(BASE_URL + "/" + id + "/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePasswordDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(ErrorMessages.NEW_PASSWORD_CANNOT_BE_OLD_PASSWORD));

        verify(userService)
                .updatePassword(id, updatePasswordDto);
    }

    @Test
    public void shouldReturnUpdatedUserForValidOldPasswordAndNewPasswordWhenUpdatingPassword() throws Exception {
        UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto("password", "password");

        when(userService.updatePassword(id, updatePasswordDto))
                .thenReturn(userDto);

        mockMvc
                .perform(put(BASE_URL + "/" + id + "/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePasswordDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(userResultMatchers("$.", userDto));

        verify(userService)
                .updatePassword(id, updatePasswordDto);
    }
    @Test
    public void shouldReturnBadRequestForNullModeratorIdWhenAddingSubredditModerator() throws Exception {
        ModeratorUpdateDto nullModeratorUpdateDto = new ModeratorUpdateDto(null, username);

        String expectedMessage = messageSource.getMessage(
                "moderatorId.required",
                null,
                LocaleContextHolder.getLocale());

        mockMvc
                .perform(put(BASE_URL + "/" + subredditTitle + "/moderators/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nullModeratorUpdateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.moderatorId").value(expectedMessage));
    }

    @Test
    public void shouldReturnBadRequestForNullUpdatedModeratorIdWhenAddingSubredditModerator() throws Exception {
        ModeratorUpdateDto nullModeratorUpdateDto = new ModeratorUpdateDto(id, null);

        String expectedMessage = messageSource.getMessage(
                "moderatorUsername.required",
                null,
                LocaleContextHolder.getLocale());

        mockMvc
                .perform(put(BASE_URL + "/" + subredditTitle + "/moderators/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nullModeratorUpdateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.updatedModeratorUsername").value(expectedMessage));
    }
    @Test
    public void shouldReturnBadRequestForNullUpdatedModeratorIdAndNullUpdatedModeratorIdWhenAddingSubredditModerator() throws Exception {
        ModeratorUpdateDto nullModeratorUpdateDto = new ModeratorUpdateDto(null, null);

        String moderatorIdExpectedMessage = messageSource.getMessage(
                "moderatorId.required",
                null,
                LocaleContextHolder.getLocale());

        String moderatorUsernameExpectedMessage = messageSource.getMessage(
                "moderatorUsername.required",
                null,
                LocaleContextHolder.getLocale());

        mockMvc
                .perform(put(BASE_URL + "/" + subredditTitle + "/moderators/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nullModeratorUpdateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.moderatorId").value(moderatorIdExpectedMessage))
                .andExpect(jsonPath("$.updatedModeratorUsername").value(moderatorUsernameExpectedMessage));
    }


    @Test
    public void shouldReturnForbiddenForUserMissingModeratorPrivilegesWhenAddingSubredditModerator() throws Exception {
        ModeratorUpdateDto moderatorUpdateDto = new ModeratorUpdateDto(id, username);

        when(userService.addSubredditModerator(subredditTitle, moderatorUpdateDto))
                .thenThrow(new MissingModeratorPrivilegesException(ErrorMessages.MISSING_MODERATOR_PRIVILEGES));

        mockMvc
                .perform(put(BASE_URL + "/" + subredditTitle + "/moderators/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moderatorUpdateDto)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(ErrorMessages.MISSING_MODERATOR_PRIVILEGES));

        verify(userService)
                .addSubredditModerator(subredditTitle, moderatorUpdateDto);
    }

    @Test
    public void shouldReturnBadRequestForUserWhoIsNotSubscribedToTheSubredditWhenAddingSubredditModerator() throws Exception {
        ModeratorUpdateDto moderatorUpdateDto = new ModeratorUpdateDto(id, username);

        when(userService.addSubredditModerator(subredditTitle, moderatorUpdateDto))
                .thenThrow(new UserNotSubscribedException(ErrorMessages.USER_NOT_SUBSCRIBED));

        mockMvc
                .perform(put(BASE_URL + "/" + subredditTitle + "/moderators/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moderatorUpdateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ErrorMessages.USER_NOT_SUBSCRIBED));

        verify(userService)
                .addSubredditModerator(subredditTitle, moderatorUpdateDto);
    }

    @Test
    public void shouldReturnSubredditForValidModeratorAndUpdatedModeratorWhenAddingSubredditModerator() throws Exception {
        ModeratorUpdateDto moderatorUpdateDto = new ModeratorUpdateDto(id, username);

        when(userService.addSubredditModerator(subredditTitle, moderatorUpdateDto))
                .thenReturn(userDto);

        mockMvc
                .perform(put(BASE_URL + "/" + subredditTitle + "/moderators/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moderatorUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(userResultMatchers("$.", userDto));

        verify(userService)
                .addSubredditModerator(subredditTitle, moderatorUpdateDto);
    }

    @Test
    public void shouldReturnBadRequestForNullModeratorIdWhenRemovingSubredditModerator() throws Exception {
        ModeratorUpdateDto nullModeratorUpdateDto = new ModeratorUpdateDto(null, username);

        String expectedMessage = messageSource.getMessage(
                "moderatorId.required",
                null,
                LocaleContextHolder.getLocale());

        mockMvc
                .perform(put(BASE_URL + "/" + subredditTitle + "/moderators/remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nullModeratorUpdateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.moderatorId").value(expectedMessage));
    }

    @Test
    public void shouldReturnBadRequestForNullUpdatedModeratorIdWhenRemovingSubredditModerator() throws Exception {
        ModeratorUpdateDto nullModeratorUpdateDto = new ModeratorUpdateDto(id, null);

        String expectedMessage = messageSource.getMessage(
                "moderatorUsername.required",
                null,
                LocaleContextHolder.getLocale());

        mockMvc
                .perform(put(BASE_URL + "/" + subredditTitle + "/moderators/remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nullModeratorUpdateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.updatedModeratorUsername").value(expectedMessage));
    }
    @Test
    public void shouldReturnBadRequestForNullUpdatedModeratorIdAndNullUpdatedModeratorIdWhenRemovingSubredditModerator() throws Exception {
        ModeratorUpdateDto nullModeratorUpdateDto = new ModeratorUpdateDto(null, null);

        String moderatorIdExpectedMessage = messageSource.getMessage(
                "moderatorId.required",
                null,
                LocaleContextHolder.getLocale());

        String moderatorUsernameExpectedMessage = messageSource.getMessage(
                "moderatorUsername.required",
                null,
                LocaleContextHolder.getLocale());

        mockMvc
                .perform(put(BASE_URL + "/" + subredditTitle + "/moderators/remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nullModeratorUpdateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.moderatorId").value(moderatorIdExpectedMessage))
                .andExpect(jsonPath("$.updatedModeratorUsername").value(moderatorUsernameExpectedMessage));
    }

    @Test
    public void shouldReturnForbiddenForUserMissingModeratorPrivilegesWhenRemovingSubredditModerator() throws Exception {
        ModeratorUpdateDto moderatorUpdateDto = new ModeratorUpdateDto(id, username);

        when(userService.removeSubredditModerator(subredditTitle, moderatorUpdateDto))
                .thenThrow(new MissingModeratorPrivilegesException(ErrorMessages.MISSING_MODERATOR_PRIVILEGES));

        mockMvc
                .perform(put(BASE_URL + "/" + subredditTitle + "/moderators/remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moderatorUpdateDto)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(ErrorMessages.MISSING_MODERATOR_PRIVILEGES));

        verify(userService)
                .removeSubredditModerator(subredditTitle, moderatorUpdateDto);
    }

    @Test
    public void shouldReturnSubredditForValidModeratorAndUpdatedModeratorWhenRemovingSubredditModerator() throws Exception {
        ModeratorUpdateDto moderatorUpdateDto = new ModeratorUpdateDto(id, username);

        when(userService.removeSubredditModerator(subredditTitle, moderatorUpdateDto))
                .thenReturn(userDto);

        mockMvc
                .perform(put(BASE_URL + "/" + subredditTitle + "/moderators/remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moderatorUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(userResultMatchers("$.", userDto));

        verify(userService)
                .removeSubredditModerator(subredditTitle, moderatorUpdateDto);
    }

    @Test
    public void shouldReturnBadRequestForNonMatchingPasswordsWhenDeletingUser() throws Exception {
        String password = "password";

        doThrow(new PasswordsDoNotMatchException(ErrorMessages.PASSWORDS_DO_NOT_MATCH))
                .when(userService)
                .deleteUser(id, password);

        mockMvc
                .perform(delete(BASE_URL + "/" + id + "?password=" + password))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ErrorMessages.PASSWORDS_DO_NOT_MATCH));

        verify(userService)
                .deleteUser(id, password);
    }
    @Test
    public void shouldReturnNoContentWhenDeletingUser() throws Exception {
        String password = "password";

        doNothing()
                .when(userService)
                    .deleteUser(id, password);

        mockMvc
                .perform(delete(BASE_URL + "/" + id + "?password=" + password))
                .andExpect(status().isNoContent());

        verify(userService)
                .deleteUser(id, password);
    }

    private ResultMatcher[] userResultMatchers(String prefix, UserDto dto) {
        return new ResultMatcher[] {
                jsonPath(prefix + "id").value(dto.id()),
                jsonPath(prefix + "username").value(dto.username())
        };
    }
}
