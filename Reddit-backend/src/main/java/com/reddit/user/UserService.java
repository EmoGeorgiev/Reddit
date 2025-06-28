package com.reddit.user;

import com.reddit.exception.user.NewPassWordCannotBeOldPasswordException;
import com.reddit.exception.user.PasswordsDoNotMatchException;
import com.reddit.exception.user.UserNotFoundException;
import com.reddit.exception.user.UsernameAlreadyExistsException;
import com.reddit.user.dto.UpdatePasswordDto;
import com.reddit.user.dto.UserDto;
import com.reddit.util.ErrorMessages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.function.Consumer;

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
    public Page<UserDto> getUsers(Pageable pageable) {
        return userRepository
                .findAll(pageable)
                .map(UserMapper::userToUserDto);
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

    public UserDto updateUsername(Long id, UserDto userDto) {
        RedditUser user = getUserEntity(id);
        String username = userDto.username();

        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException(ErrorMessages.USERNAME_ALREADY_EXISTS);
        }

        user.setUsername(username);

        return UserMapper.userToUserDto(user);
    }

    public UserDto updatePassword(Long id, UpdatePasswordDto updatePasswordDto) {
        RedditUser user = getUserEntity(id);
        String oldPassword = updatePasswordDto.oldPassword();
        String newPassword = updatePasswordDto.newPassword();

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new PasswordsDoNotMatchException(ErrorMessages.PASSWORDS_DO_NOT_MATCH);
        }

        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new NewPassWordCannotBeOldPasswordException(ErrorMessages.NEW_PASSWORD_CANNOT_BE_OLD_PASSWORD);
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
