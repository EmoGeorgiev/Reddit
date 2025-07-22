package com.reddit.user;

import com.reddit.user.dto.ModeratorUpdateDto;
import com.reddit.user.dto.UpdatePasswordDto;
import com.reddit.user.dto.UserDto;
import com.reddit.util.PaginationConstants;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto userDto = userService.getUser(id);
        return ResponseEntity
                .ok()
                .body(userDto);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        UserDto userDto = userService.getUserByUsername(username);
        return ResponseEntity
                .ok()
                .body(userDto);
    }

    @GetMapping
    public ResponseEntity<Page<UserDto>> getUsers(
            @PageableDefault(
                    size = PaginationConstants.USER_DEFAULT_SIZE,
                    sort = PaginationConstants.USER_DEFAULT_SORT,
                    direction = Sort.Direction.DESC) Pageable pageable) {
        Page<UserDto> users = userService.getUsers(pageable);
        return ResponseEntity
                .ok()
                .body(users);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserDto>> getUsersWhereUsernameContainsWord(
            @RequestParam String word,
            @PageableDefault(
                    size = PaginationConstants.USER_DEFAULT_SIZE,
                    sort = PaginationConstants.USER_DEFAULT_SORT,
                    direction = Sort.Direction.ASC) Pageable pageable) {
        Page<UserDto> users = userService.getUsersWhereUsernameContainsWord(word, pageable);

        return ResponseEntity
                .ok()
                .body(users);
    }

    @GetMapping("/subreddits/{subredditTitle}")
    public ResponseEntity<List<UserDto>> getUsersBySubredditTitle(@PathVariable String subredditTitle) {
        List<UserDto> users = userService.getUsersBySubredditTitle(subredditTitle);
        return ResponseEntity
                .ok()
                .body(users);
    }

    @GetMapping("/subreddits/{subredditTitle}/moderators")
    public ResponseEntity<List<UserDto>> getModeratorsBySubredditTitle(@PathVariable String subredditTitle) {
        List<UserDto> moderators = userService.getModeratorsBySubredditTitle(subredditTitle);
        return ResponseEntity
                .ok()
                .body(moderators);
    }

    @PutMapping("/{id}/username")
    public ResponseEntity<UserDto> updateUsername(@PathVariable Long id, @RequestBody @Valid UserDto userDto) {
        UserDto updatedUser = userService.updateUsername(id, userDto);
        return ResponseEntity
                .ok()
                .body(updatedUser);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<UserDto> updatePassword(@PathVariable Long id, @RequestBody @Valid UpdatePasswordDto updatePasswordDto) {
        UserDto userDto = userService.updatePassword(id, updatePasswordDto);
        return ResponseEntity
                .ok()
                .body(userDto);
    }

    @PutMapping("/{subredditTitle}/moderators/add")
    public ResponseEntity<UserDto> addSubredditModerator(@PathVariable String subredditTitle, @RequestBody @Valid ModeratorUpdateDto moderatorUpdateDto) {
        UserDto subredditDto = userService.addSubredditModerator(subredditTitle, moderatorUpdateDto);
        return ResponseEntity
                .ok()
                .body(subredditDto);
    }

    @PutMapping("/{subredditTitle}/moderators/remove")
    public ResponseEntity<UserDto> removeSubredditModerator(@PathVariable String subredditTitle, @RequestBody @Valid ModeratorUpdateDto moderatorUpdateDto) {
        UserDto subredditDto = userService.removeSubredditModerator(subredditTitle, moderatorUpdateDto);
        return ResponseEntity
                .ok()
                .body(subredditDto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId, @RequestParam String password) {
        userService.deleteUser(userId, password);
        return ResponseEntity
                .noContent()
                .build();
    }
}
