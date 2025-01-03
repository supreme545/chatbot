package com.chatbot.dao.impl;

import com.chatbot.dao.ChatMessageDAO;
import com.chatbot.model.ChatMessage;
import com.chatbot.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChatMessageDAOImpl implements ChatMessageDAO {
    
    @Override
    public ChatMessage create(ChatMessage message) throws SQLException {
        String sql = "INSERT INTO chat_messages (user_id, message, response, timestamp, is_processed) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setLong(1, message.getUserId());
            stmt.setString(2, message.getMessage());
            stmt.setString(3, message.getResponse());
            stmt.setTimestamp(4, Timestamp.valueOf(message.getTimestamp()));
            stmt.setBoolean(5, message.isProcessed());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating chat message failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    message.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating chat message failed, no ID obtained.");
                }
            }
            
            return message;
        }
    }
    
    @Override
    public ChatMessage findById(Long id) throws SQLException {
        String sql = "SELECT * FROM chat_messages WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToChatMessage(rs);
                }
            }
        }
        return null;
    }
    
    @Override
    public List<ChatMessage> findByUserId(Long userId) throws SQLException {
        String sql = "SELECT * FROM chat_messages WHERE user_id = ? ORDER BY timestamp DESC";
        List<ChatMessage> messages = new ArrayList<>();
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    messages.add(mapResultSetToChatMessage(rs));
                }
            }
        }
        return messages;
    }
    
    @Override
    public List<ChatMessage> findUnprocessedMessages() throws SQLException {
        String sql = "SELECT * FROM chat_messages WHERE is_processed = false ORDER BY timestamp ASC";
        List<ChatMessage> messages = new ArrayList<>();
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                messages.add(mapResultSetToChatMessage(rs));
            }
        }
        return messages;
    }
    
    @Override
    public void update(ChatMessage message) throws SQLException {
        String sql = "UPDATE chat_messages SET response = ?, is_processed = ? WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, message.getResponse());
            stmt.setBoolean(2, message.isProcessed());
            stmt.setLong(3, message.getId());
            
            stmt.executeUpdate();
        }
    }
    
    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM chat_messages WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
    
    private ChatMessage mapResultSetToChatMessage(ResultSet rs) throws SQLException {
        ChatMessage message = new ChatMessage();
        message.setId(rs.getLong("id"));
        message.setUserId(rs.getLong("user_id"));
        message.setMessage(rs.getString("message"));
        message.setResponse(rs.getString("response"));
        message.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
        message.setProcessed(rs.getBoolean("is_processed"));
        return message;
    }
}
