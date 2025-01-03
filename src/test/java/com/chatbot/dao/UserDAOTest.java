package com.chatbot.dao;

import com.chatbot.dao.impl.UserDAOImpl;
import com.chatbot.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {
    private UserDAO userDAO;
    private User testUser;

    @BeforeEach
    void setUp() {
        userDAO = new UserDAOImpl();
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setPassword("password123");
        testUser.setEmail("test@example.com");
        testUser.setRole("USER");
    }

    @Test
    void testCreateUser() throws SQLException {
        User createdUser = userDAO.create(testUser);
        assertNotNull(createdUser.getId());
        assertEquals(testUser.getUsername(), createdUser.getUsername());
        
        // Cleanup
        userDAO.delete(createdUser.getId());
    }

    @Test
    void testFindByUsername() throws SQLException {
        User createdUser = userDAO.create(testUser);
        User foundUser = userDAO.findByUsername(testUser.getUsername());
        
        assertNotNull(foundUser);
        assertEquals(createdUser.getId(), foundUser.getId());
        assertEquals(testUser.getUsername(), foundUser.getUsername());
        
        // Cleanup
        userDAO.delete(createdUser.getId());
    }

    @Test
    void testUpdateUser() throws SQLException {
        User createdUser = userDAO.create(testUser);
        createdUser.setEmail("updated@example.com");
        
        userDAO.update(createdUser);
        User updatedUser = userDAO.findById(createdUser.getId());
        
        assertEquals("updated@example.com", updatedUser.getEmail());
        
        // Cleanup
        userDAO.delete(createdUser.getId());
    }

    @Test
    void testDeleteUser() throws SQLException {
        User createdUser = userDAO.create(testUser);
        assertNotNull(userDAO.findById(createdUser.getId()));
        
        userDAO.delete(createdUser.getId());
        assertNull(userDAO.findById(createdUser.getId()));
    }
}
