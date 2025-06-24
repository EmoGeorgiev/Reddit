package com.reddit.authentication;

import com.reddit.authentication.dto.LoginResponseDto;
import com.reddit.security.JwtService;
import com.reddit.security.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final JwtService jwtService;

    public AuthenticationService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public LoginResponseDto login(Authentication authentication) {
        SecurityUser user = (SecurityUser) authentication.getPrincipal();

        Long id = user.getId();
        String username = user.getUsername();
        String token = jwtService.generateToken(authentication);

        return new LoginResponseDto(
                id,
                username,
                token
        );
    }
}
