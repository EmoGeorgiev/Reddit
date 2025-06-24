package com.reddit.authentication;

import com.reddit.authentication.dto.LoginResponseDto;
import com.reddit.authentication.dto.SignUpDto;
import com.reddit.user.dto.UserDto;
import com.reddit.user.UserService;
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
    private final UserService userService;

    public AuthenticationController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/api/signup")
    public ResponseEntity<UserDto> signUpUser(@RequestBody @Valid SignUpDto signUpDto) {
        UserDto userDto = userService.addUser(signUpDto.username(), signUpDto.password());

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
