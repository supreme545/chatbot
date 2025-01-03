package com.chatbot.service;

import com.chatbot.model.User;
import java.sql.SQLException;

public interface UserService {
    User registerUser(User user) throws SQLException;
    User authenticateUser(String username, String password) throws SQLException;
    User updateUserProfile(User user) throws SQLException;
    String hashPassword(String password);
}
