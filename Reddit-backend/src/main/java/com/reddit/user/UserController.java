package com.reddit.user;

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

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        UserDto userDto = userService.getUser(id);
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
                    direction = Sort.Direction.DESC) Pageable pageable) {
        Page<UserDto> users = userService.getUsersWhereUsernameContainsWord(word, pageable);

        return ResponseEntity
                .ok()
                .body(users);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
