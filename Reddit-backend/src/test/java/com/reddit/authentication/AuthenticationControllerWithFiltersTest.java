package com.reddit.authentication;

import com.reddit.authentication.dto.LoginResponseDto;
import com.reddit.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerWithFiltersTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private AuthenticationService authenticationService;
    @MockitoBean
    private UserService userService;

    @Test
    public void shouldReturnIsUnauthorizedForWrongCredentials() throws Exception {
        mockMvc
                .perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void shouldReturnLoginResponseDtoForValidCredentials() throws Exception {
        LoginResponseDto loginResponseDto = new LoginResponseDto(1L, "username", "testToken");

        when(authenticationService.login(any(Authentication.class)))
                .thenReturn(loginResponseDto);

        mockMvc
                .perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"username\", \"password\": \"password\"}")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(loginResponseDto.id()))
                .andExpect(jsonPath("$.username").value(loginResponseDto.username()))
                .andExpect(jsonPath("$.token").value(loginResponseDto.token()));

        verify(authenticationService)
                .login(any(Authentication.class));
    }
}
