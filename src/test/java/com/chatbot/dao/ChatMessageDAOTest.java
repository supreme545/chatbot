package com.chatbot.dao;

import com.chatbot.dao.impl.ChatMessageDAOImpl;
import com.chatbot.model.ChatMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ChatMessageDAOTest {
    private ChatMessageDAO chatMessageDAO;
    private ChatMessage testMessage;

    @BeforeEach
    void setUp() {
        chatMessageDAO = new ChatMessageDAOImpl();
        testMessage = new ChatMessage();
        testMessage.setUserId(1L); // Assuming user with ID 1 exists
        testMessage.setMessage("Test message");
        testMessage.setTimestamp(LocalDateTime.now());
        testMessage.setProcessed(false);
    }

    @Test
    void testCreateMessage() throws SQLException {
        ChatMessage createdMessage = chatMessageDAO.create(testMessage);
        assertNotNull(createdMessage.getId());
        assertEquals(testMessage.getMessage(), createdMessage.getMessage());
        
        // Cleanup
        chatMessageDAO.delete(createdMessage.getId());
    }

    @Test
    void testFindByUserId() throws SQLException {
        ChatMessage createdMessage = chatMessageDAO.create(testMessage);
        List<ChatMessage> messages = chatMessageDAO.findByUserId(testMessage.getUserId());
        
        assertFalse(messages.isEmpty());
        assertTrue(messages.stream()
            .anyMatch(m -> m.getId().equals(createdMessage.getId())));
        
        // Cleanup
        chatMessageDAO.delete(createdMessage.getId());
    }

    @Test
    void testUpdateMessage() throws SQLException {
        ChatMessage createdMessage = chatMessageDAO.create(testMessage);
        createdMessage.setResponse("Test response");
        createdMessage.setProcessed(true);
        
        chatMessageDAO.update(createdMessage);
        ChatMessage updatedMessage = chatMessageDAO.findById(createdMessage.getId());
        
        assertEquals("Test response", updatedMessage.getResponse());
        assertTrue(updatedMessage.isProcessed());
        
        // Cleanup
        chatMessageDAO.delete(createdMessage.getId());
    }

    @Test
    void testFindUnprocessedMessages() throws SQLException {
        ChatMessage createdMessage = chatMessageDAO.create(testMessage);
        List<ChatMessage> unprocessedMessages = chatMessageDAO.findUnprocessedMessages();
        
        assertFalse(unprocessedMessages.isEmpty());
        assertTrue(unprocessedMessages.stream()
            .anyMatch(m -> m.getId().equals(createdMessage.getId())));
        
        // Cleanup
        chatMessageDAO.delete(createdMessage.getId());
    }
}
