package com.chatbot.dao;

import com.chatbot.model.User;
import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    User create(User user) throws SQLException;
    User findById(Long id) throws SQLException;
    User findByUsername(String username) throws SQLException;
    List<User> findAll() throws SQLException;
    User update(User user) throws SQLException;
    void delete(Long id) throws SQLException;
}
