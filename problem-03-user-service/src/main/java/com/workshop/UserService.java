package com.workshop;

import java.util.Optional;

/**
 * User service with dependencies on UserRepository and EmailService.
 * <p>
 * Tests should be written that:<p>
 * - Mock the dependencies using @Mock and @InjectMocks<p>
 * - Verify interactions with mocks using verify()<p>
 * - Stub method calls using when().thenReturn()<p>
 * - Test both successful and error scenarios
 */
public class UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public User createUser(String username, String email) {
        // Validate inputs
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if (!emailService.isValidEmailFormat(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }

        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User(username, email);
        User savedUser = userRepository.save(user);

        emailService.sendWelcomeEmail(email, username);

        return savedUser;
    }

    public Optional<User> getUserById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return Optional.empty();
        }

        return userRepository.findByUsername(username);
    }

    public void deactivateUser(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        User user = userOpt.get();
        user.setActive(false);
        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        if (userRepository.findById(userId).isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        userRepository.deleteById(userId);
    }

    public void updateEmail(Long userId, String newEmail) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        if (newEmail == null || newEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if (!emailService.isValidEmailFormat(newEmail)) {
            throw new IllegalArgumentException("Invalid email format");
        }

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        // Check if email is already taken by another user
        Optional<User> existingUser = userRepository.findByEmail(newEmail);
        if (existingUser.isPresent() && !existingUser.get().getId().equals(userId)) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = userOpt.get();
        user.setEmail(newEmail);
        userRepository.save(user);
    }
}
