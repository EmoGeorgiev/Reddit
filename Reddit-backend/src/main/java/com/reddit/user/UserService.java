package com.reddit.user;

import com.reddit.exception.user.PasswordsDoNotMatchException;
import com.reddit.exception.user.UserNotFoundException;
import com.reddit.exception.user.UsernameAlreadyExistsException;
import com.reddit.user.dto.UserDto;
import com.reddit.util.ErrorMessages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
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
    public Page<UserDto> getUsersWhereUsernameContainsWord(String word, Pageable pageable) {
        return userRepository
                .findByUsernameContainingIgnoreCase(word, pageable)
                .map(UserMapper::userToUserDto);
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

        return UserMapper.userToUserDto(user);
    }

    public UserDto updatePassword(Long id, String oldPassword, String newPassword) {
        RedditUser user = getUserEntity(id);

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new PasswordsDoNotMatchException(ErrorMessages.PASSWORDS_DO_NOT_MATCH);
        }

        String encodedNewPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedNewPassword);

        return UserMapper.userToUserDto(user);
    }

    public void deleteUser(Long id) {
        RedditUser user = getUserEntity(id);

        removeUserFromCollections(user);

        userRepository.deleteById(id);
    }

    private void removeUserFromCollections(RedditUser user) {
        applyToCollection(user.getComments(), comment -> comment.setUser(null));
        applyToCollection(user.getPosts(), post -> post.setUser(null));

        removeUserFromCollection(user.getSubscribedTo(), subreddit -> subreddit.getUsers().remove(user));
        removeUserFromCollection(user.getModerated(), subreddit -> subreddit.getModerators().remove(user));
    }

    private <T> void applyToCollection(Collection<T> collection, Consumer<T> applier) {
        collection.forEach(applier);
    }

    private <T> void removeUserFromCollection(Collection<T> collection, Consumer<T> remover) {
        applyToCollection(collection, remover);
        collection.clear();
    }
}
