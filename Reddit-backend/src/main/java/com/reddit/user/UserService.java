package com.reddit.user;

import com.reddit.exception.user.PasswordsDoNotMatchException;
import com.reddit.exception.user.UserNotFoundException;
import com.reddit.exception.user.UsernameAlreadyExistsException;
import com.reddit.util.ErrorMessages;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public RedditUser getUserEntity(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND));
    }
    @Transactional(readOnly = true)
    public UserDto getUser(Long id) {
        return UserMapper.userToUserDto(getUserEntity(id));
    }

    @Transactional(readOnly = true)
    public List<UserDto> getUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(UserMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UserDto> getUsersWhereUsernameContainsWord(String word) {
        return userRepository
                .findByUsernameContainingIgnoreCase(word)
                .stream()
                .map(UserMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    public UserDto addUser(String username, String password) {
        RedditUser user = new RedditUser();

        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException(ErrorMessages.USERNAME_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(password);
        user.setUsername(username);
        user.setPassword(encodedPassword);

        RedditUser savedUser = userRepository.save(user);

        return UserMapper.userToUserDto(savedUser);
    }

    public UserDto updateUsername(UserDto userDto) {
        RedditUser user = getUserEntity(userDto.id());
        String username = userDto.username();

        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException(ErrorMessages.USERNAME_ALREADY_EXISTS);
        }

        user.setUsername(username);

        RedditUser savedUser = userRepository.save(user);

        return UserMapper.userToUserDto(savedUser);
    }

    public UserDto updatePassword(Long id, String oldPassword, String newPassword) {
        RedditUser user = getUserEntity(id);

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new PasswordsDoNotMatchException(ErrorMessages.PASSWORDS_DO_NOT_MATCH);
        }

        String encodedNewPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedNewPassword);

        RedditUser savedUser = userRepository.save(user);

        return UserMapper.userToUserDto(savedUser);
    }

    public void deleteUser(Long id) {
        RedditUser user = getUserEntity(id);

        user.getComments()
            .forEach(comment -> comment.setUser(null));

        user.getPosts()
            .forEach(post -> post.setUser(null));

        userRepository.deleteById(id);
    }
}
