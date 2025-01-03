package com.chatbot.service.impl;

import com.chatbot.dao.UserDAO;
import com.chatbot.model.User;
import com.chatbot.service.UserService;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Base64;

public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User registerUser(User user) throws SQLException {
        if (userDAO.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        user.setPassword(hashPassword(user.getPassword()));
        return userDAO.create(user);
    }

    @Override
    public User authenticateUser(String username, String password) throws SQLException {
        User user = userDAO.findByUsername(username);
        if (user != null && user.getPassword().equals(hashPassword(password))) {
            return user;
        }
        throw new IllegalArgumentException("Invalid username or password");
    }

    @Override
    public User updateUserProfile(User user) throws SQLException {
        User existingUser = userDAO.findById(user.getId());
        if (existingUser == null) {
            throw new IllegalArgumentException("User not found");
        }
        
        // Only update password if it's provided
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(hashPassword(user.getPassword()));
        } else {
            user.setPassword(existingUser.getPassword());
        }
        
        return userDAO.update(user);
    }

    @Override
    public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
