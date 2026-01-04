package de.ityreh.home.blog.controller;

import de.ityreh.home.blog.dto.UserCreateDto;
import de.ityreh.home.blog.dto.UserResponseDto;
import de.ityreh.home.blog.dto.UserUpdateDto;
import de.ityreh.home.blog.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * Create a new user.
     *
     * @param createDto the user creation data
     * @return the created user
     */
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateDto createDto) {
        log.info("REST request to create user: {}", createDto.getUsername());
        UserResponseDto response = userService.createUser(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get a user by ID.
     *
     * @param id the user ID
     * @return the user
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        log.info("REST request to get user by ID: {}", id);
        UserResponseDto response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get a user by username.
     *
     * @param username the username
     * @return the user
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDto> getUserByUsername(@PathVariable String username) {
        log.info("REST request to get user by username: {}", username);
        UserResponseDto response = userService.getUserByUsername(username);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all users.
     *
     * @return list of all users
     */
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        log.info("REST request to get all users");
        List<UserResponseDto> response = userService.getAllUsers();
        return ResponseEntity.ok(response);
    }

    /**
     * Update an existing user.
     *
     * @param id        the user ID
     * @param updateDto the update data
     * @return the updated user
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDto updateDto) {
        log.info("REST request to update user with ID: {}", id);
        UserResponseDto response = userService.updateUser(id, updateDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete a user.
     *
     * @param id the user ID
     * @return no content response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("REST request to delete user with ID: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
