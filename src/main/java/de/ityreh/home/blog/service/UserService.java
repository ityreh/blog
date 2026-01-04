package de.ityreh.home.blog.service;

import de.ityreh.home.blog.dto.UserCreateDto;
import de.ityreh.home.blog.dto.UserResponseDto;
import de.ityreh.home.blog.dto.UserUpdateDto;
import de.ityreh.home.blog.entity.User;
import de.ityreh.home.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for managing users.
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Create a new user.
     *
     * @param createDto the user creation data
     * @return the created user
     */
    public UserResponseDto createUser(UserCreateDto createDto) {
        log.debug("Creating user with username: {}", createDto.getUsername());

        if (userRepository.existsByUsername(createDto.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + createDto.getUsername());
        }

        if (userRepository.existsByEmail(createDto.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + createDto.getEmail());
        }

        User user = User.builder()
                .username(createDto.getUsername())
                .email(createDto.getEmail())
                .password(passwordEncoder.encode(createDto.getPassword()))
                .firstName(createDto.getFirstName())
                .lastName(createDto.getLastName())
                .enabled(true)
                .build();

        user = userRepository.save(user);
        log.info("User created successfully with ID: {}", user.getId());

        return toResponseDto(user);
    }

    /**
     * Get a user by ID.
     *
     * @param id the user ID
     * @return the user
     */
    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long id) {
        log.debug("Fetching user with ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
        return toResponseDto(user);
    }

    /**
     * Get a user by username.
     *
     * @param username the username
     * @return the user
     */
    @Transactional(readOnly = true)
    public UserResponseDto getUserByUsername(String username) {
        log.debug("Fetching user with username: {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
        return toResponseDto(user);
    }

    /**
     * Get all users.
     *
     * @return list of all users
     */
    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        log.debug("Fetching all users");
        return userRepository.findAll().stream()
                .map(this::toResponseDto)
                .toList();
    }

    /**
     * Update an existing user.
     *
     * @param id        the user ID
     * @param updateDto the update data
     * @return the updated user
     */
    public UserResponseDto updateUser(Long id, UserUpdateDto updateDto) {
        log.debug("Updating user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));

        if (updateDto.getEmail() != null && !updateDto.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(updateDto.getEmail())) {
                throw new IllegalArgumentException("Email already exists: " + updateDto.getEmail());
            }
            user.setEmail(updateDto.getEmail());
        }

        if (updateDto.getFirstName() != null) {
            user.setFirstName(updateDto.getFirstName());
        }

        if (updateDto.getLastName() != null) {
            user.setLastName(updateDto.getLastName());
        }

        if (updateDto.getEnabled() != null) {
            user.setEnabled(updateDto.getEnabled());
        }

        user = userRepository.save(user);
        log.info("User updated successfully with ID: {}", user.getId());

        return toResponseDto(user);
    }

    /**
     * Delete a user.
     *
     * @param id the user ID
     */
    public void deleteUser(Long id) {
        log.debug("Deleting user with ID: {}", id);

        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }

        userRepository.deleteById(id);
        log.info("User deleted successfully with ID: {}", id);
    }

    /**
     * Convert User entity to UserResponseDto.
     */
    private UserResponseDto toResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .enabled(user.getEnabled())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
