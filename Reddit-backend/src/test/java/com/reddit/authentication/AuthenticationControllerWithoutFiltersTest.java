package com.reddit.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reddit.authentication.dto.SignUpDto;
import com.reddit.exception.user.UsernameAlreadyExistsException;
import com.reddit.user.UserService;
import com.reddit.user.dto.UserDto;
import com.reddit.util.ErrorMessages;
import com.reddit.util.ValidationConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.reddit.util.TestUtils.*;
import static com.reddit.util.TestUtils.getStringWithFixedLength;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthenticationControllerWithoutFiltersTest {
    private static final String BASE_URL = "/api/signup";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MessageSource messageSource;
    @MockitoBean
    private AuthenticationService authenticationService;
    @MockitoBean
    private UserService userService;

    private final String username = "username";
    private final String password = "password";
    private final SignUpDto signUpDto = new SignUpDto(username, password);

    @Test
    public void shouldReturnConflictForExistingUsernameWhenCreatingUser() throws Exception {
        when(userService.addUser(username, password))
                .thenThrow(new UsernameAlreadyExistsException(ErrorMessages.USERNAME_ALREADY_EXISTS));

        mockMvc
                .perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(ErrorMessages.USERNAME_ALREADY_EXISTS));

        verify(userService)
                .addUser(username, password);
    }

    @Test
    public void shouldReturnBadRequestForBlankUsernameWhenCreatingUser() throws Exception {
        SignUpDto notValidSignUpDto = new SignUpDto(BLANK_STRING, password);

        String expectedMessage = messageSource.getMessage(
                "username.required",
                null,
                LocaleContextHolder.getLocale());

        mockMvc
                .perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notValidSignUpDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.username").value(expectedMessage));
    }

    @Test
    public void shouldReturnBadRequestForNotValidUsernameWhenCreatingUser() throws Exception {
        String overMaxSizeUsername = getStringWithFixedLength(ValidationConstants.USERNAME_MAX + 1);

        SignUpDto notValidSignUpDto = new SignUpDto(overMaxSizeUsername, password);

        Object[] args = getArgs(ValidationConstants.USERNAME_MIN, ValidationConstants.USERNAME_MAX);
        String expectedMessage = messageSource.getMessage(
                "username.size.test",
                args,
                LocaleContextHolder.getLocale());

        mockMvc
                .perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notValidSignUpDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.username").value(expectedMessage));
    }

    @Test
    public void shouldReturnBadRequestForBlankPasswordWhenCreatingUser() throws Exception {
        SignUpDto notValidSignUpDto = new SignUpDto(username, BLANK_STRING);

        String expectedMessage = messageSource.getMessage(
                "password.required",
                null,
                LocaleContextHolder.getLocale());

        mockMvc
                .perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notValidSignUpDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password").value(expectedMessage));
    }

    @Test
    public void shouldReturnBadRequestForNotValidPasswordWhenCreatingUser() throws Exception {
        String overMaxSizePassword = getStringWithFixedLength(ValidationConstants.PASSWORD_MAX + 1);

        SignUpDto notValidSignUpDto = new SignUpDto(username, overMaxSizePassword);

        Object[] args = getArgs(ValidationConstants.PASSWORD_MIN, ValidationConstants.PASSWORD_MAX);
        String expectedMessage = messageSource.getMessage(
                "password.size.test",
                args,
                LocaleContextHolder.getLocale());

        mockMvc
                .perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notValidSignUpDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password").value(expectedMessage));
    }

    @Test
    public void shouldReturnUserWhenCreatingUser() throws Exception {
        UserDto userDto = new UserDto(1L, username);

        when(userService.addUser(username, password))
                .thenReturn(userDto);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userDto.id()))
                .andExpect(jsonPath("$.username").value(userDto.username()));

        verify(userService).addUser(username, password);
    }
}
