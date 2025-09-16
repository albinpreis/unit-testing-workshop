package com.workshop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        // TODO: Arrange - set up mocks
        String username = "johndoe";
        String email = "john@example.com";
        User savedUser = new User(username, email);
        savedUser.setId(1L);

        // Stub the mock behaviors
        // when(emailService.isValidEmailFormat(email)).thenReturn(?);
        // when(userRepository.existsByUsername(username)).thenReturn(?);
        // when(userRepository.existsByEmail(email)).thenReturn(?);
        // when(userRepository.save(any(User.class))).thenReturn(?);

        // TODO: Act - call the method

        // TODO: Assert - verify the result
        // assertNotNull(?);
        // assertEquals(?, ?);

        // TODO: Verify interactions
        // verify(emailService).isValidEmailFormat(?);
        // verify(userRepository).existsByUsername(?);
        // verify(userRepository).existsByEmail(?);
        // verify(userRepository).save(?);
        // verify(emailService).sendWelcomeEmail(?, ?);
    }

    // TODO: Write test for createUser with invalid email
    @Test
    void testCreateUser_InvalidEmail() {
        // Test should verify that IllegalArgumentException is thrown
        // and that no user is saved and no email is sent
    }

    // TODO: Write test for createUser with existing username
    @Test
    void testCreateUser_ExistingUsername() {

    }

    // TODO: Write test for createUser with existing email

    // TODO: Write test for getUserById with valid ID

    // TODO: Write test for getUserById with invalid ID (should throw exception)

    // TODO: Write test for getUserByUsername

    // TODO: Write test for deactivateUser - success case

    // TODO: Write test for deactivateUser - user not found

    // TODO: Write test for deleteUser - success case

    // TODO: Write test for deleteUser - user not found

    // TODO: Write test for updateEmail - success case

    // TODO: Write test for updateEmail - email already taken by another user
}
