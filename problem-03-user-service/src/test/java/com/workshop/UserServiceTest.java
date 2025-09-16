package com.workshop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UserService userService;

    @Test
    void testCreateUser_Success() {
        // Arrange - set up mocks
        String username = "johndoe";
        String email = "john@example.com";
        User savedUser = new User(username, email);
        savedUser.setId(1L);

        // Stub the mock behaviors
        when(emailService.isValidEmailFormat(email)).thenReturn(true);
        when(userRepository.existsByUsername(username)).thenReturn(false);
        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act - call the method
        User result = userService.createUser(username, email);

        // Assert - verify the result
        assertNotNull(result);
        assertEquals(savedUser.getId(), result.getId());
        assertEquals(username, result.getUsername());
        assertEquals(email, result.getEmail());

        // Verify interactions
        verify(emailService).isValidEmailFormat(email);
        verify(userRepository).existsByUsername(username);
        verify(userRepository).existsByEmail(email);
        verify(userRepository).save(any(User.class));
        verify(emailService).sendWelcomeEmail(email, username);
    }

    @Test
    void testCreateUser_InvalidEmail() {
        String username = "johndoe";
        String email = "invalid-email";

        when(emailService.isValidEmailFormat(email)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.createUser(username, email)
        );
        assertEquals("Invalid email format", exception.getMessage());

        verify(emailService).isValidEmailFormat(email);
        verify(userRepository, never()).save(any(User.class));
        verify(emailService, never()).sendWelcomeEmail(anyString(), anyString());
    }

    @Test
    void testCreateUser_ExistingUsername() {
        String username = "existinguser";
        String email = "new@example.com";

        when(emailService.isValidEmailFormat(email)).thenReturn(true);
        when(userRepository.existsByUsername(username)).thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.createUser(username, email)
        );
        assertEquals("Username already exists", exception.getMessage());

        verify(emailService).isValidEmailFormat(email);
        verify(userRepository).existsByUsername(username);
        verify(userRepository, never()).existsByEmail(anyString());
        verify(userRepository, never()).save(any(User.class));
        verify(emailService, never()).sendWelcomeEmail(anyString(), anyString());
    }

    @Test
    void testCreateUser_ExistingEmail() {
        String username = "newuser";
        String email = "existing@example.com";

        when(emailService.isValidEmailFormat(email)).thenReturn(true);
        when(userRepository.existsByUsername(username)).thenReturn(false);
        when(userRepository.existsByEmail(email)).thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.createUser(username, email)
        );
        assertEquals("Email already exists", exception.getMessage());

        verify(emailService).isValidEmailFormat(email);
        verify(userRepository).existsByUsername(username);
        verify(userRepository).existsByEmail(email);
        verify(userRepository, never()).save(any(User.class));
        verify(emailService, never()).sendWelcomeEmail(anyString(), anyString());
    }

    @Test
    void testCreateUser_NullUsername() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.createUser(null, "test@example.com")
        );
        assertEquals("Username cannot be empty", exception.getMessage());

        verifyNoInteractions(emailService);
        verifyNoInteractions(userRepository);
    }

    @Test
    void testCreateUser_EmptyEmail() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.createUser("testuser", "")
        );
        assertEquals("Email cannot be empty", exception.getMessage());

        verifyNoInteractions(emailService);
        verifyNoInteractions(userRepository);
    }

    @Test
    void testGetUserById_ValidId() {
        Long userId = 1L;
        User expectedUser = new User("testuser", "test@example.com");
        expectedUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        Optional<User> result = userService.getUserById(userId);

        assertTrue(result.isPresent());
        assertEquals(expectedUser, result.get());
        verify(userRepository).findById(userId);
    }

    @Test
    void testGetUserById_InvalidId() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.getUserById(0L)
        );
        assertEquals("Invalid user ID", exception.getMessage());

        verifyNoInteractions(userRepository);
    }

    @Test
    void testGetUserById_NullId() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.getUserById(null)
        );
        assertEquals("Invalid user ID", exception.getMessage());

        verifyNoInteractions(userRepository);
    }

    @Test
    void testGetUserByUsername() {
        String username = "testuser";
        User expectedUser = new User(username, "test@example.com");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(expectedUser));

        Optional<User> result = userService.getUserByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(expectedUser, result.get());
        verify(userRepository).findByUsername(username);
    }

    @Test
    void testGetUserByUsername_NullUsername() {
        Optional<User> result = userService.getUserByUsername(null);

        assertTrue(result.isEmpty());
        verifyNoInteractions(userRepository);
    }

    @Test
    void testDeactivateUser_Success() {
        Long userId = 1L;
        User user = new User("testuser", "test@example.com");
        user.setId(userId);
        user.setActive(true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.deactivateUser(userId);

        assertFalse(user.isActive());
        verify(userRepository).findById(userId);
        verify(userRepository).save(user);
    }

    @Test
    void testDeactivateUser_UserNotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.deactivateUser(userId)
        );
        assertEquals("User not found", exception.getMessage());

        verify(userRepository).findById(userId);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testDeactivateUser_InvalidId() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.deactivateUser(-1L)
        );
        assertEquals("Invalid user ID", exception.getMessage());

        verifyNoInteractions(userRepository);
    }

    @Test
    void testDeleteUser_Success() {
        Long userId = 1L;
        User user = new User("testuser", "test@example.com");
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.deleteUser(userId);

        verify(userRepository).findById(userId);
        verify(userRepository).deleteById(userId);
    }

    @Test
    void testDeleteUser_UserNotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.deleteUser(userId)
        );
        assertEquals("User not found", exception.getMessage());

        verify(userRepository).findById(userId);
        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    void testDeleteUser_InvalidId() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.deleteUser(null)
        );
        assertEquals("Invalid user ID", exception.getMessage());

        verifyNoInteractions(userRepository);
    }

    @Test
    void testUpdateEmail_Success() {
        Long userId = 1L;
        String newEmail = "newemail@example.com";
        User user = new User("testuser", "old@example.com");
        user.setId(userId);

        when(emailService.isValidEmailFormat(newEmail)).thenReturn(true);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(newEmail)).thenReturn(Optional.empty());

        userService.updateEmail(userId, newEmail);

        assertEquals(newEmail, user.getEmail());
        verify(emailService).isValidEmailFormat(newEmail);
        verify(userRepository).findById(userId);
        verify(userRepository).findByEmail(newEmail);
        verify(userRepository).save(user);
    }

    @Test
    void testUpdateEmail_EmailAlreadyTakenByAnotherUser() {
        Long userId = 1L;
        String newEmail = "taken@example.com";
        User currentUser = new User("testuser", "old@example.com");
        currentUser.setId(userId);

        User existingUser = new User("anotheruser", newEmail);
        existingUser.setId(2L); // Different user ID

        when(emailService.isValidEmailFormat(newEmail)).thenReturn(true);
        when(userRepository.findById(userId)).thenReturn(Optional.of(currentUser));
        when(userRepository.findByEmail(newEmail)).thenReturn(Optional.of(existingUser));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.updateEmail(userId, newEmail)
        );
        assertEquals("Email already exists", exception.getMessage());

        verify(emailService).isValidEmailFormat(newEmail);
        verify(userRepository).findById(userId);
        verify(userRepository).findByEmail(newEmail);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testUpdateEmail_SameUserUpdatingToSameEmail() {
        Long userId = 1L;
        String email = "same@example.com";
        User user = new User("testuser", "old@example.com");
        user.setId(userId);

        when(emailService.isValidEmailFormat(email)).thenReturn(true);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user)); // Same user

        userService.updateEmail(userId, email);

        assertEquals(email, user.getEmail());
        verify(emailService).isValidEmailFormat(email);
        verify(userRepository).findById(userId);
        verify(userRepository).findByEmail(email);
        verify(userRepository).save(user);
    }

    @Test
    void testUpdateEmail_UserNotFound() {
        Long userId = 1L;
        String newEmail = "new@example.com";

        when(emailService.isValidEmailFormat(newEmail)).thenReturn(true);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.updateEmail(userId, newEmail)
        );
        assertEquals("User not found", exception.getMessage());

        verify(emailService).isValidEmailFormat(newEmail);
        verify(userRepository).findById(userId);
        verify(userRepository, never()).findByEmail(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testUpdateEmail_InvalidEmailFormat() {
        Long userId = 1L;
        String invalidEmail = "invalid-email";

        when(emailService.isValidEmailFormat(invalidEmail)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.updateEmail(userId, invalidEmail)
        );
        assertEquals("Invalid email format", exception.getMessage());

        verify(emailService).isValidEmailFormat(invalidEmail);
        verifyNoInteractions(userRepository);
    }
}