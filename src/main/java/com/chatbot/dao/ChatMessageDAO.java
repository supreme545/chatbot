package com.chatbot.dao;

import com.chatbot.model.ChatMessage;
import java.sql.SQLException;
import java.util.List;

public interface ChatMessageDAO {
    ChatMessage create(ChatMessage message) throws SQLException;
    ChatMessage findById(Long id) throws SQLException;
    List<ChatMessage> findByUserId(Long userId) throws SQLException;
    List<ChatMessage> findUnprocessedMessages() throws SQLException;
    void update(ChatMessage message) throws SQLException;
    void delete(Long id) throws SQLException;
}
