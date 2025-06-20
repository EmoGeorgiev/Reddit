package com.example.reddit.authentication;

import com.example.reddit.security.JpaUserDetailsService;
import com.example.reddit.user.UserDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JpaUserDetailsService userDetailsService;

    public AuthenticationController(AuthenticationService authenticationService, JpaUserDetailsService userDetailsService) {
        this.authenticationService = authenticationService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/api/signup")
    public ResponseEntity<UserDto> signUpUser(@RequestBody @Valid SignUpDto signUpDto) {
        UserDto userDto = userDetailsService.createUser(signUpDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userDto);
    }

    @PostMapping("/api/login")
    public ResponseEntity<LoginResponseDto> login(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        LoginResponseDto loginResponseDto = authenticationService.login(authentication);

        return ResponseEntity
                .ok()
                .body(loginResponseDto);
    }
}
