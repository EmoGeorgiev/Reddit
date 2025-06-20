package com.example.reddit.security;

import com.example.reddit.authentication.SignUpDto;
import com.example.reddit.exception.UsernameAlreadyExistsException;
import com.example.reddit.user.RedditUser;
import com.example.reddit.user.UserDto;
import com.example.reddit.user.UserMapper;
import com.example.reddit.user.UserRepository;
import com.example.reddit.util.ErrorMessages;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public JpaUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto createUser(SignUpDto signUpDto) {
        RedditUser user = new RedditUser();

        if (userRepository.findByUsername(signUpDto.username()).isPresent()) {
            throw new UsernameAlreadyExistsException(ErrorMessages.USERNAME_ALREADY_EXISTS);
        }

        user.setUsername(signUpDto.username());
        user.setPassword(passwordEncoder.encode(signUpDto.password()));

        RedditUser savedUser = userRepository.save(user);

        return UserMapper.userToUserDto(savedUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorMessages.USERNAME_DOES_NOT_EXIST));
    }
}
