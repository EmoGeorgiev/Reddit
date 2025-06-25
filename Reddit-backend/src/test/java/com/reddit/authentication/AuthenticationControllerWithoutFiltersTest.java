package com.reddit.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reddit.authentication.dto.SignUpDto;
import com.reddit.exception.user.UsernameAlreadyExistsException;
import com.reddit.user.UserService;
import com.reddit.user.dto.UserDto;
import com.reddit.util.ErrorMessages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthenticationControllerWithoutFiltersTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
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
                .perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpDto))
                        .with(csrf()))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(ErrorMessages.USERNAME_ALREADY_EXISTS));

        verify(userService)
                .addUser(username, password);
    }
    @Test
    public void shouldReturnUserWhenCreatingUser() throws Exception {
        UserDto userDto = new UserDto(1L, username);

        when(userService.addUser(username, password))
                .thenReturn(userDto);

        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userDto.id()))
                .andExpect(jsonPath("$.username").value(userDto.username()));

        verify(userService).addUser(username, password);
    }

}
