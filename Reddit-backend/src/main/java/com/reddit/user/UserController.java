package com.reddit.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> users = userService.getUsers();
        return ResponseEntity
                .ok()
                .body(users);
    }

    @PutMapping("/{id}/username")
    public ResponseEntity<UserDto> updateUsername(@PathVariable Long id, @RequestBody @Valid UserDto userDto) {
        UserDto updatedUser = userService.updateUsername(userDto);
        return ResponseEntity
                .ok()
                .body(updatedUser);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<UserDto> updatePassword(@PathVariable Long id, @RequestBody @Valid UpdatePasswordDto updatePasswordDto) {
        UserDto userDto = userService.updatePassword(id, updatePasswordDto.oldPassword(), updatePasswordDto.newPassword());
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
