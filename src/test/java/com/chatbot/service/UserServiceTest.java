package com.chatbot.service;

import com.chatbot.dao.UserDAO;
import com.chatbot.model.User;
import com.chatbot.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    private UserService userService;
    private User testUser;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userDAO);
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setPassword("password123");
        testUser.setEmail("test@example.com");
        testUser.setRole("USER");
    }

    @Test
    void testRegisterUserSuccess() throws SQLException {
        when(userDAO.findByUsername(testUser.getUsername())).thenReturn(null);
        when(userDAO.create(any(User.class))).thenReturn(testUser);

        User registeredUser = userService.registerUser(testUser);

        assertNotNull(registeredUser);
        assertEquals(testUser.getUsername(), registeredUser.getUsername());
        assertNotEquals("password123", registeredUser.getPassword(), "Password should be hashed");
        verify(userDAO).create(any(User.class));
    }

    @Test
    void testRegisterUserDuplicate() throws SQLException {
        when(userDAO.findByUsername(testUser.getUsername())).thenReturn(testUser);

        assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(testUser);
        });

        verify(userDAO, never()).create(any(User.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "ab", "a".repeat(51)})
    void testRegisterUserInvalidUsername(String username) {
        testUser.setUsername(username);
        assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(testUser);
        });
    }

    @Test
    void testAuthenticateUserSuccess() throws SQLException {
        String rawPassword = "password123";
        testUser.setPassword(userService.hashPassword(rawPassword));
        when(userDAO.findByUsername(testUser.getUsername())).thenReturn(testUser);

        User authenticatedUser = userService.authenticateUser(testUser.getUsername(), rawPassword);

        assertNotNull(authenticatedUser);
        assertEquals(testUser.getUsername(), authenticatedUser.getUsername());
    }

    @Test
    void testAuthenticateUserWrongPassword() throws SQLException {
        testUser.setPassword(userService.hashPassword("password123"));
        when(userDAO.findByUsername(testUser.getUsername())).thenReturn(testUser);

        assertThrows(IllegalArgumentException.class, () -> {
            userService.authenticateUser(testUser.getUsername(), "wrongpassword");
        });
    }

    @Test
    void testAuthenticateUserNotFound() throws SQLException {
        when(userDAO.findByUsername(testUser.getUsername())).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            userService.authenticateUser(testUser.getUsername(), "password123");
        });
    }

    @Test
    void testUpdateUserSuccess() throws SQLException {
        String newEmail = "newemail@example.com";
        testUser.setEmail(newEmail);
        when(userDAO.update(testUser)).thenReturn(testUser);

        User updatedUser = userService.updateUser(testUser);

        assertNotNull(updatedUser);
        assertEquals(newEmail, updatedUser.getEmail());
        verify(userDAO).update(testUser);
    }

    @Test
    void testUpdateUserNotFound() throws SQLException {
        when(userDAO.update(testUser)).thenThrow(new SQLException("User not found"));

        assertThrows(RuntimeException.class, () -> {
            userService.updateUser(testUser);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"short", "verylongpasswordthatexceedsmaximumlength", ""})
    void testValidatePasswordStrength(String password) {
        assertThrows(IllegalArgumentException.class, () -> {
            testUser.setPassword(password);
            userService.registerUser(testUser);
        });
    }

    @Test
    void testChangePasswordSuccess() throws SQLException {
        String oldPassword = "password123";
        String newPassword = "newpassword123";
        testUser.setPassword(userService.hashPassword(oldPassword));
        
        when(userDAO.findById(testUser.getId())).thenReturn(testUser);
        when(userDAO.update(any(User.class))).thenReturn(testUser);

        User updatedUser = userService.changePassword(testUser.getId(), oldPassword, newPassword);

        assertNotNull(updatedUser);
        assertNotEquals(oldPassword, updatedUser.getPassword());
        verify(userDAO).update(any(User.class));
    }

    @Test
    void testChangePasswordWrongOldPassword() throws SQLException {
        String oldPassword = "password123";
        testUser.setPassword(userService.hashPassword(oldPassword));
        
        when(userDAO.findById(testUser.getId())).thenReturn(testUser);

        assertThrows(IllegalArgumentException.class, () -> {
            userService.changePassword(testUser.getId(), "wrongpassword", "newpassword123");
        });

        verify(userDAO, never()).update(any(User.class));
    }
}
